package com.sala_spectacole.service;

import com.sala_spectacole.domain.*;
import com.sala_spectacole.persistence.LocRezervatRepository;

import java.util.*;

public class LocRezervatService {
    private static LocRezervatService instance;
    private final LocRezervatRepository locRezervatRepository = LocRezervatRepository.getInstance();
    private final SalaService salaService = SalaService.getInstance();
    private final SpectacolService spectacolService = SpectacolService.getInstance();
    private final PersoanaService persoanaService = PersoanaService.getInstance();

    private LocRezervatService() {
    }

    public static LocRezervatService getInstance() {
        if (instance == null) {
            instance = new LocRezervatService();
        }
        return instance;
    }

    public void cumparareBilet(String cnp, String numeSpectacol) {
        Spectacol spectacol = spectacolService.findSpectacol(numeSpectacol);
        if (spectacol.getNumeSpectacol() != null) {
            System.out.println("Cate locuri doriti?");
            int nr_locuri = new Scanner(System.in).nextInt();
            System.out.println("Vreti sa vedeti locurile libere? da/nu");
            String mesaj = new Scanner(System.in).next();
            if (mesaj.equals("da"))
                locuriLibere(numeSpectacol);
            for (int i = 0; i < nr_locuri; i++) {
                System.out.println("Ce tip de loc doriti? LocCategoria1/LocCategoria2/LocLoja/LocBalcon");
                String categorie = new Scanner(System.in).next();
                System.out.println("Numar rand: (ex: R1)");
                String rand = new Scanner(System.in).next();
                System.out.println("Numar loc: ");
                int nrLoc = new Scanner(System.in).nextInt();
                List<LocCategoria1> zona = spectacolService.salaSpectacol(numeSpectacol).get(categorie);
                for (LocCategoria1 loc : zona) {
                    if (loc.getNrLoc().equals(nrLoc) && loc.getNrRand().equals(rand)) {
                        LocRezervat locRezervat = new LocRezervat(loc, cnp, numeSpectacol, loc.getPret());
                        if (findLoc(categorie, nrLoc, rand, cnp, numeSpectacol))
                            locRezervatRepository.saveLocRezervat(locRezervat);
                        else
                            System.out.println("Locul e deja rezervat");
                    }
                }

            }
        }
    }

    public void anulareBilete(String cnp, String numeSpectacol) {
        locRezervatRepository.deleteLocuri(cnp, numeSpectacol);
    }

    public void anulareSpectacol(String numeSpectacol) {
        locRezervatRepository.deleteSpectacol(numeSpectacol);
    }

    public void locuriLibere(String numeSpectacol) {
        List<LocRezervat> locuriRezervate = locRezervatRepository.locuriRezervateSpectacol(numeSpectacol);
        Map<String, List<LocCategoria1>> asezare = spectacolService.salaSpectacol(numeSpectacol);
        for (LocRezervat locRezervat : locuriRezervate) {
            for (String categorie : asezare.keySet()) {
                String clas = locRezervat.getLoc().getClass().getName();
                if (clas.contains(categorie)) {
                    List<LocCategoria1> locuri = asezare.get(categorie);
                    for (LocCategoria1 loc : locuri) {
                        if (loc.getNrLoc().equals(locRezervat.getLoc().getNrLoc()) && loc.getNrRand().equals(locRezervat.getLoc().getNrRand())) {
                            loc.setNrLoc(0);
                            break;
                        }
                    }
                }
            }
        }
        salaService.printSala(asezare, spectacolService.findSpectacol(numeSpectacol).getSala());
    }

    public void spectatori(String numeSpectacol) {
        List<LocRezervat> locuriRezervate = locRezervatRepository.locuriRezervateSpectacol(numeSpectacol);
        Set<String> spectatori = new HashSet<>();
        for (LocRezervat loc : locuriRezervate) {
            String cnp = loc.getCnpPersoana();
            Persoana pers = persoanaService.findPersoana(cnp);
            spectatori.add(pers.getNume() + " " + pers.getPrenume());
        }
        if (spectatori.isEmpty()) {
            System.out.println("Nu avem spectatori");
        } else {
            for (String spectator : spectatori) {
                System.out.println(spectator);
            }
        }
    }

    public void printareBilet(String cnp, String numeSpectacol) {
        List<LocRezervat> locuri = locRezervatRepository.locSpectacol(cnp, numeSpectacol);
        Persoana persoana = persoanaService.findPersoana(cnp);
        System.out.println(persoana.getNume() + " " + persoana.getPrenume());
        for (LocRezervat loc : locuri) {
            String categorie = loc.getLoc().getClass().getSimpleName();
            System.out.println(categorie + ": " + loc.getLoc().getNrRand() + " " + loc.getLoc().getNrLoc());
        }
    }

    public double profitSpectacol(String numeSpectacol) {
        double profit = 0;
        List<LocRezervat> locuri = locRezervatRepository.locuriRezervateSpectacol(numeSpectacol);
        for (LocRezervat loc : locuri) {
            profit += loc.getPret();
        }
        return profit;
    }

    public boolean findLoc(String categorie, int nrLoc, String rand, String cnp, String numeSpectacol) {
        LocCategoria1 loc;
        switch (categorie) {
            case "LocCategoria2": {
                loc = new LocCategoria2(nrLoc, rand);
            }
            break;
            case "LocLoja": {
                loc = new LocLoja(nrLoc, rand);
            }
            break;
            case "LocBalcon": {
                loc = new LocBalcon(nrLoc, rand);
            }
            break;
            default:
                loc = new LocCategoria1(nrLoc, rand);
        }
        LocRezervat locRezervat = new LocRezervat(loc, cnp, numeSpectacol, 0);
        LocRezervat locGasit = locRezervatRepository.findLoc(locRezervat);
        return locGasit.getNumeSpectacol() != null;

    }
}
