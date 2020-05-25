package com.sala_spectacole.service;

import com.sala_spectacole.domain.Persoana;
import com.sala_spectacole.exception.NotGoodCNPException;
import com.sala_spectacole.persistence.PersoanaRepository;


public class PersoanaService {
    private static PersoanaService instance;
    private final PersoanaRepository persoanaRepository = PersoanaRepository.getInstance();

    private PersoanaService() {
    }

    public static PersoanaService getInstance() {
        if (instance == null) {
            instance = new PersoanaService();
        }
        return instance;
    }

    public void savePersoana(String cnp, String nume, String prenume) {
        if (cnp.length() != 12) throw new NotGoodCNPException("CNP-ul nu are lungimea care trebuie.");
        else {
            Persoana pers = new Persoana(nume, prenume, cnp);
            persoanaRepository.savePersona(pers);
        }
    }

    public Persoana findPersoana(String cnp) {
        Persoana pers;
        if (cnp.length() != 12) throw new NotGoodCNPException("CNP-ul nu are lungimea care trebuie.");
        else {
            pers = persoanaRepository.selectPerson(cnp);
        }
        return pers;
    }

    public void updatePersoana(String nume, String cnp) {
        if (findPersoana(cnp).getNume() != null) {
            persoanaRepository.updateNume(nume, cnp);
        }
    }

    public void deletePersoana(String cnp) {
        persoanaRepository.deletePersoana(cnp);
    }

}
