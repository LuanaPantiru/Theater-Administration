package com.sala_spectacole.service;

import com.sala_spectacole.domain.Calendar;
import com.sala_spectacole.domain.LocCategoria1;
import com.sala_spectacole.domain.Spectacol;
import com.sala_spectacole.persistence.CalendarRepository;
import com.sala_spectacole.persistence.SpectacolRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class SpectacolService {
    private static SpectacolService instance;
    private final SpectacolRepository spectacolRepository = SpectacolRepository.getInstance();
    private final CalendarRepository calendarRepository = CalendarRepository.getInstance();
    private final SalaService salaService = SalaService.getInstance();

    private SpectacolService() {
    }

    public static SpectacolService getInstance() {
        if (instance == null) {
            instance = new SpectacolService();
        }
        return instance;
    }

    public void saveSpectacol(String numeSpectacol, String numeSala, Integer zi, Integer luna, Integer an, double pret) {
        if (!spectacolRepository.verifSalaData(numeSala, zi, luna, an)) {
            if (salaService.findSala(numeSala)) {
                Spectacol spectacol = new Spectacol(numeSpectacol, numeSala, pret, zi, luna, an);
                spectacolRepository.saveSpectacol(spectacol);
            }
        }
    }

    public boolean existSpectacol(String numeSpectacol) {
        Spectacol spectacol = spectacolRepository.findSpectacol(numeSpectacol);
        return spectacol.getNumeSpectacol() != null;
    }

    public Spectacol findSpectacol(String numeSpectacol) {
        return spectacolRepository.findSpectacol(numeSpectacol);
    }

    public void printSpectacole() {
        LocalDate dataLocala = LocalDate.now();
        int zi = dataLocala.getDayOfMonth();
        int luna = dataLocala.getMonthValue();
        int an = dataLocala.getYear();
        List<Calendar> spectacoleActive = calendarRepository.spectacoleActive(zi, luna, an);
        for (Calendar spectacolData : spectacoleActive) {
            spectacolData.printDate();
        }
    }

    public List<String> salaSpectacole(String numeSala) {
        return spectacolRepository.salaSpectacole(numeSala);
    }

    public void anulareSpectacol(String numeSpectacol) {
        spectacolRepository.deleteSpectacol(numeSpectacol);
    }

    public Map<String, List<LocCategoria1>> salaSpectacol(String numeSpectacol) {
        Spectacol spectacol = spectacolRepository.findSpectacol(numeSpectacol);
        String numeSala = spectacol.getSala();
        Map<String, List<LocCategoria1>> asezare = salaService.formaSala(numeSala);
        salaService.setarePret(spectacol.getPretNominal(), asezare);
        return asezare;
    }

    public void modificareSala(String numeSalaNou, String numeSalaVechi) {
        spectacolRepository.updateSala(numeSalaNou, numeSalaVechi);
    }
}
