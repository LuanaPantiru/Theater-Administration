package com.sala_spectacole.service;

import com.sala_spectacole.domain.LocCategoria1;
import com.sala_spectacole.domain.Sala;
import com.sala_spectacole.persistence.SalaRepository;

import java.util.List;
import java.util.Map;

public class SalaService {
    private static SalaService instance;
    private final SalaRepository salaRepository = SalaRepository.getInstance();

    private SalaService() {
    }

    public static SalaService getInstance() {
        if (instance == null) {
            instance = new SalaService();
        }
        return instance;
    }

    public void saveSala(String numeSala, Integer locuriCategoria1, Integer randuriCategoria1,
                         Integer locuriCategoria2, Integer randuriCatgoria2, Integer locuriLoja,
                         Integer randuriLoja, Integer locuriBalcon, Integer randuriBalcon) {
        Sala sala = new Sala(numeSala, locuriCategoria1, randuriCategoria1, locuriCategoria2, randuriCatgoria2,
                locuriLoja, randuriLoja, locuriBalcon, randuriBalcon);
        salaRepository.saveSala(sala);
    }

    public boolean findSala(String numeSala) {
        Sala sala = salaRepository.selectSala(numeSala);
        return sala.getNume() != null;
    }

    public void printSali() {
        List<String> sali = salaRepository.sali();
        if (sali.size() > 0) {
            int i = 0;
            for (String sala : sali) {
                i += 1;
                System.out.println(i + "." + sala);
            }
        }
    }

    public Map<String, List<LocCategoria1>> formaSala(String numeSala) {
        Sala sala = salaRepository.selectSala(numeSala);
        return salaRepository.formareSala(sala);
    }

    public void printSala(Map<String, List<LocCategoria1>> asezare, String numeSala) {
        System.out.println(numeSala);
        for (String zona : asezare.keySet()) {
            System.out.println(zona);
            List<LocCategoria1> categorie = asezare.get(zona);
            String rand = "R1";
            boolean ok = false;
            for (LocCategoria1 loc : categorie) {
                if (!ok) {
                    System.out.print("(pret : " + loc.getPret() + " lei)\n");
                    ok = true;
                }
                String nrRand = loc.getNrRand();
                if (nrRand.compareTo(rand) == 0)
                    System.out.print(loc.getNrLoc() + " ");
                else {
                    System.out.print("\n" + loc.getNrLoc() + " ");
                    rand = nrRand;
                }
            }
            System.out.print("\n");
        }
    }

    public void deleteSala(String numeSala) {
        salaRepository.deleteSala(numeSala);
    }

    public void updateSala(String numeNou, String numeVechi) {
        salaRepository.updateSala(numeNou, numeVechi);
    }

    public void setarePret(double pret, Map<String, List<LocCategoria1>> asezare) {
        salaRepository.setarePret(pret, asezare);
    }
}
