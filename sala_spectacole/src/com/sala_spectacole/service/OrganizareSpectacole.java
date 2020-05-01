package com.sala_spectacole.service;

import com.sala_spectacole.domain.*;
import com.sala_spectacole.domain.Calendar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class OrganizareSpectacole {
    private List<Spectacol> spectacoleActive = new ArrayList<>();
    private List<Spectacol> spectacoleInchise = new ArrayList<>();
    private List<Sala> sali = new ArrayList<>();
    private List<Calendar> calendar = new ArrayList<>();

    public List<Sala> getSali() {
        return sali;
    }

    public List<Spectacol> getSpectacoleActive() {
        return spectacoleActive;
    }

    public List<Spectacol> getSpectacoleInchise() {
        return spectacoleInchise;
    }

    public List<Calendar> getCalendar() {
        return calendar;
    }

    public void setSpectacoleActive(List<Spectacol> spectacoleActive) {
        this.spectacoleActive = spectacoleActive;
    }

    public void setSpectacoleInchise(List<Spectacol> spectacoleInchise) {
        this.spectacoleInchise = spectacoleInchise;
    }

    public void setSali(List<Sala> sali) {
        this.sali = sali;
    }

    public void setCalendar(List<Calendar> calendar) {
        this.calendar = calendar;
    }

    //actiuni legate de spectacole
    public void addSpectacol() {
        while (true) {
            System.out.println("Nume sala: ");
            Scanner input = new Scanner(System.in);
            String numeSala = input.next();
            Sala salaGasita = findSala(numeSala);
            if (salaGasita != null) {
                System.out.println("Nume spectacol: ");
                input = new Scanner(System.in);
                String nume = input.next();
                System.out.println("Data: zi:");
                input = new Scanner(System.in);
                Integer zi = input.nextInt();
                System.out.println("luna: ");
                input = new Scanner(System.in);
                Integer luna = input.nextInt();
                System.out.println("an: ");
                input = new Scanner(System.in);
                Integer an = input.nextInt();
                System.out.println("Pret de start: ");
                input = new Scanner(System.in);
                double pret = input.nextDouble();
                if (!salaLibera(numeSala, zi, luna, an)) {
                    Sala sala = new Sala(numeSala, salaGasita.getLocuriCategoria1(), salaGasita.getRanduriCategoria1(), salaGasita.getLocuriCategoria2(), salaGasita.getRanduriCategoria2(), salaGasita.getLocuriLoja(), salaGasita.getRanduriLoja(), salaGasita.getLocuriBalcon(), salaGasita.getRanduriBalcon());
                    Spectacol spec = new Spectacol(nume, sala, pret, zi, luna, an);
                    Calendar date = new Calendar(zi, luna, an, nume);
                    spectacoleActive.add(spec);
                    calendar.add(date);
                    Collections.sort(spectacoleActive);
                    Collections.sort(calendar);
                    break;
                } else System.out.println("Sala indisponibila in perioada dorita");
            } else {
                System.out.println("Sala incorecta");
            }

        }

    }

    public void printSpectacole() {
        for (Spectacol spectacol : spectacoleActive) {
            System.out.println(spectacol.getNumeSpectacol() + ", data:" + spectacol.getZi() + "." + spectacol.getLuna() + "." + spectacol.getAn());
        }
    }

    public Spectacol findSpectacol(String nume) {

        for (Spectacol spectacol : spectacoleActive) {
            if (spectacol.getNumeSpectacol().equals(nume))
                return spectacol;
        }
        for (Spectacol spectacol : spectacoleInchise)
            if (spectacol.getNumeSpectacol().equals(nume))
                return spectacol;
        return null;
    }

    public double profit(Spectacol spectacol) {
        double profit = 0;
        List<LocRezervat> locuriRezervate = spectacol.getLocuriRezervateRezervat();
        for (LocRezervat loc : locuriRezervate) {
            profit += loc.getLoc().getPret();
        }
        return profit;
    }

    public void locuriLibere(Spectacol spectacol) {
        Map<String, List<LocCategoria1>> sala = spectacol.getSala().getAsezare();
        List<LocRezervat> locuriRezervate = spectacol.getLocuriRezervateRezervat();
        for (LocRezervat locRezervat : locuriRezervate) {
            for (String categorie : sala.keySet()) {
                String clas = locRezervat.getLoc().getClass().getName();
                if (clas.contains(categorie)) {
                    List<LocCategoria1> locuri = sala.get(categorie);
                    for (LocCategoria1 loc : locuri) {
                        if (loc.getNrLoc().equals(locRezervat.getLoc().getNrLoc()) && loc.getNrRand().equals(locRezervat.getLoc().getNrRand())) {
                            loc.setNrLoc(0);
                            break;
                        }
                    }
                }
            }
        }
        printSala(spectacol.getSala());
        for (String categorie : sala.keySet()) {
            List<LocCategoria1> locuri = sala.get(categorie);
            int nrLoc = 1;
            int nrRand = 1;
            for (LocCategoria1 loc : locuri) {
                if (!loc.getNrRand().equals("R" + nrRand)) {
                    nrRand += 1;
                    nrLoc = 1;
                }
                if (loc.getNrLoc() == 0)
                    loc.setNrLoc(nrLoc);
                nrLoc += 1;
            }
        }
    }

    public void spectatori(Spectacol spectacol) {
        List<LocRezervat> locuriRezervate = spectacol.getLocuriRezervateRezervat();
        Set<Persoana> spectatori = new HashSet<>();
        for (LocRezervat loc : locuriRezervate)
            spectatori.add(loc.getPersoana());
        for (Persoana persoana : spectatori)
            System.out.println(persoana.getNume() + " " + persoana.getPrenume());
    }
    //actiuni legate de sali


    public void printSali() {
        for (Sala sala : sali)
            System.out.println(sala.getNume());
        System.out.println("Vreti sa vizualizati o sala? da/nu");
        Scanner input = new Scanner(System.in);
        if (input.next().equals("da")) {
            System.out.println("Nume sala: ");
            input = new Scanner(System.in);
            Sala salaGasita = findSala(input.next());
            if (salaGasita != null)
                printSala(salaGasita);
            else System.out.println("Nu s-a gasit sala.");
        }
    }

    public void addSala() {
        System.out.println("Nume sala: ");
        Scanner input = new Scanner(System.in);
        String nume = input.next();
        System.out.println("Numar locuri categoria1: ");
        input = new Scanner(System.in);
        Integer nrLocuriCategoria1 = input.nextInt();
        System.out.println("Numar randuri categoria1: ");
        input = new Scanner(System.in);
        Integer nrRanduriCategoria1 = input.nextInt();
        System.out.println("Numar locuri categoria2: ");
        input = new Scanner(System.in);
        Integer nrLocuriCategoria2 = input.nextInt();
        System.out.println("Numar randuri categoria2: ");
        input = new Scanner(System.in);
        Integer nrRanduriCategoria2 = input.nextInt();
        System.out.println("Numar locuri loja: ");
        input = new Scanner(System.in);
        Integer nrLocuriLoja = input.nextInt();
        System.out.println("Numar randuri loja: ");
        input = new Scanner(System.in);
        Integer nrRanduriLoja = input.nextInt();
        System.out.println("Numar locuri balcon: ");
        input = new Scanner(System.in);
        Integer nrLocuriBalcon = input.nextInt();
        System.out.println("Numar randuri balcon: ");
        input = new Scanner(System.in);
        Integer nrRanduriBalcon = input.nextInt();
        Sala s = new Sala(nume, nrLocuriCategoria1, nrRanduriCategoria1, nrLocuriCategoria2, nrRanduriCategoria2, nrLocuriLoja, nrRanduriLoja, nrLocuriBalcon, nrRanduriBalcon);
        sali.add(s);
    }

    public Sala findSala(String nume) {
        for (Sala sala : sali) {
            if (sala.getNume().equals(nume))
                return sala;
        }
        return null;
    }

    public boolean salaLibera(String nume, Integer zi, Integer luna, Integer an) {
        for (Spectacol spectacol : spectacoleActive)
            if (spectacol.getNumeSpectacol().equals(nume) && spectacol.getZi().equals(zi) && spectacol.getLuna().equals(luna) && spectacol.getAn().equals(an))
                return true;
        return false;
    }

    public void printSala(Sala sala) {
        System.out.println(sala.getNume());
        Map<String, List<LocCategoria1>> asezare = sala.getAsezare();
        for (String zona : asezare.keySet()) {
            System.out.print(zona+ " ");
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

    //actiuni legate de bilete
    public void anulareBilet(Spectacol spectacol, Persoana persoana) {
        List<LocRezervat> locuriRezervate = spectacol.getLocuriRezervateRezervat();
        for (int i = 0; i < locuriRezervate.size(); i++) {
            LocRezervat loc = locuriRezervate.get(i);
            if (loc.getPersoana().getNume().equals(persoana.getNume()) && loc.getPersoana().getPrenume().equals(persoana.getPrenume())) {
                locuriRezervate.remove(i);
                i -= 1;
            }
        }
    }

    public void printBilet(Spectacol spectacol, Persoana persoana) {
        System.out.println(persoana.getNume() + " " + persoana.getPrenume());
        List<LocRezervat> locuriRezervate = spectacol.getLocuriRezervateRezervat();
        for (LocRezervat loc : locuriRezervate) {
            if (loc.getPersoana().getPrenume().equals(persoana.getPrenume()) && loc.getPersoana().getNume().equals(persoana.getNume())) {
                String categorie = loc.getLoc().getClass().getSimpleName();
                System.out.println(categorie + ": " + loc.getLoc().getNrRand() + " " + loc.getLoc().getNrLoc());
            }
        }
    }


    public void cumparareBilete(Spectacol spectacol, Persoana persoana) {
        System.out.println("Cate locuri doriti?");
        int nr_locuri = new Scanner(System.in).nextInt();
        System.out.println("Vreti sa vedeti locurile libere? da/nu");
        String mesaj = new Scanner(System.in).next();
        if (mesaj.equals("da"))
            locuriLibere(spectacol);
        for (int i = 0; i < nr_locuri; i++) {
            System.out.println("Ce tip de loc doriti? LocCategoria1/LocCategoria2/LocLoja/LocBalcon");
            Scanner input = new Scanner(System.in);
            String categorie = input.next();
            System.out.println("Numar rand: (ex: R1)");
            input = new Scanner(System.in);
            String rand = input.next();
            System.out.println("Numar loc: ");
            input = new Scanner(System.in);
            Integer nrLoc = input.nextInt();
            List<LocCategoria1> zona = spectacol.getSala().categorie(categorie);
            for (LocCategoria1 loc : zona) {
                if (loc.getNrLoc().equals(nrLoc) && loc.getNrRand().equals(rand)) {
                    LocRezervat locRezervat = new LocRezervat(loc, persoana, loc.getPret());
                    spectacol.addBilet(locRezervat);
                }
            }

        }

    }

    //actiuni legate de calendar
    public void cancelSpectacole() {
        LocalDate data = LocalDate.now();
        int zi = data.getDayOfMonth();
        int luna = data.getMonthValue();
        int an = data.getYear();
        List<Spectacol> spectacole = new ArrayList<>();
        for (Calendar c : calendar) {
            if (c.beforeDate(zi, luna, an)) {
                Spectacol s = findSpectacol(c.getNumeSpectacol());
                spectacoleActive.remove(s);
                spectacole.add(s);
            }
        }
        setSpectacoleInchise(spectacole);
    }

    public double profitAn(int an) {
        double suma = 0;
        for (Calendar date : calendar) {
            if (date.getAn() == an) {
                Spectacol spect = findSpectacol(date.getNumeSpectacol());
                if (spect != null)
                    suma += profit(spect);
            } else if (date.getAn() > an)
                break;
        }
        return suma;
    }

    public void audit(String actiune) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("istoric.txt", true))) {
            LocalDate data = LocalDate.now();
            bufferedWriter.write(actiune + "," + data);
            bufferedWriter.newLine();

        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully wrote action.");
    }
}
