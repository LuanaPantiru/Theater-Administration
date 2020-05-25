package com.sala_spectacole.service;

import com.sala_spectacole.domain.*;
import com.sala_spectacole.exception.DuplicateSalaException;
import com.sala_spectacole.exception.DuplicateSpectacolException;
import com.sala_spectacole.exception.NotExistsException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class OrganizareSpectacole {
    private static final SalaService sali = SalaService.getInstance();
    private static final SpectacolService spectacole = SpectacolService.getInstance();
    private static final LocRezervatService locuriRezervate = LocRezervatService.getInstance();
    private static final PersoanaService persoane = PersoanaService.getInstance();
    private final CalendarService dates = CalendarService.getInstance();

    public OrganizareSpectacole() {
        dates.readCalendarFromFile();
    }

    public Persoana autentificare() {
        System.out.println("CNP: ");
        String cnp = new Scanner(System.in).next();
        Persoana pers = persoane.findPersoana(cnp);
        if (pers.getNume() == null) {
            System.out.println("Nume: ");
            String nume = new Scanner(System.in).next();
            System.out.println("Prenume: ");
            String prenume = new Scanner(System.in).next();
            persoane.savePersoana(cnp, nume, prenume);
            pers.setCnp(cnp);
            pers.setNume(nume);
            pers.setPrenume(prenume);
            audit("inregistrare");
        } else {
            System.out.println("V-ati autentificat cu succes");
            audit("autentificare");
        }
        return pers;
    }

    public void anulareCont(Persoana pers) {
        System.out.println("Sigur vreti sa va stergeti contul? da/nu");
        String raspuns = new Scanner(System.in).next();
        if (raspuns.equals("da")) {
            persoane.deletePersoana(pers.getCnp());
            audit("anulareCont");
        }

    }

    public void updateNume(Persoana pers) {
        System.out.println("Nume nou: ");
        String numeNou = new Scanner(System.in).next();
        persoane.updatePersoana(numeNou, pers.getCnp());
        pers.setNume(numeNou);
        audit("updateNume");
    }

    public void addSala() {
        System.out.println("Nume sala: ");
        String numeSala = new Scanner(System.in).next();
        if (sali.findSala(numeSala)) throw new DuplicateSalaException("Exista deja aceasta sala");
        else {
            System.out.println("Numar locuri categoria1: ");
            Integer nrLocuriCategoria1 = new Scanner(System.in).nextInt();
            System.out.println("Numar randuri categoria1: ");
            Integer nrRanduriCategoria1 = new Scanner(System.in).nextInt();
            System.out.println("Numar locuri categoria2: ");
            Integer nrLocuriCategoria2 = new Scanner(System.in).nextInt();
            System.out.println("Numar randuri categoria2: ");
            Integer nrRanduriCategoria2 = new Scanner(System.in).nextInt();
            System.out.println("Numar locuri loja: ");
            Integer nrLocuriLoja = new Scanner(System.in).nextInt();
            System.out.println("Numar randuri loja: ");
            Integer nrRanduriLoja = new Scanner(System.in).nextInt();
            System.out.println("Numar locuri balcon: ");
            Integer nrLocuriBalcon = new Scanner(System.in).nextInt();
            System.out.println("Numar randuri balcon: ");
            Integer nrRanduriBalcon = new Scanner(System.in).nextInt();
            sali.saveSala(numeSala, nrLocuriCategoria1, nrRanduriCategoria1, nrLocuriCategoria2,
                    nrRanduriCategoria2, nrLocuriLoja, nrRanduriLoja, nrLocuriBalcon, nrRanduriBalcon);
            audit("adaugareSala");
        }
    }

    public void addSpectacol() {
        System.out.println("Nume spectacol: ");
        String numeSpectacol = new Scanner(System.in).next();
        if (spectacole.existSpectacol(numeSpectacol))
            throw new DuplicateSpectacolException("Exista deja acest spectacol");
        else {
            System.out.println("Nume sala: ");
            String numeSala = new Scanner(System.in).next();
            System.out.println("Data: zi:");
            int zi = new Scanner(System.in).nextInt();
            System.out.println("luna: ");
            int luna = new Scanner(System.in).nextInt();
            System.out.println("an: ");
            int an = new Scanner(System.in).nextInt();
            System.out.println("Pret de start: ");
            double pret = new Scanner(System.in).nextDouble();
            spectacole.saveSpectacol(numeSpectacol, numeSala, zi, luna, an, pret);
            dates.addDate(numeSpectacol, zi, luna, an);
            audit("adaugareSpectacol");
        }
    }

    public void printSpectacole() {
        spectacole.printSpectacole();
        audit("afisareSpectacole");
    }

    public void profit() {
        System.out.println("Nume spectacol: ");
        String numeSpectacol = new Scanner(System.in).next();
        if (!spectacole.existSpectacol(numeSpectacol)) throw new NotExistsException("Nu exista acest spectacol");
        else {
            double profit = locuriRezervate.profitSpectacol(numeSpectacol);
            if (profit > 0)
                System.out.println("Spectacolul " + numeSpectacol + " are un profit de " + profit + " lei.");
            else
                System.out.println("Nu avem profit pentru spectacolol " + numeSpectacol);
            audit("calculareProfit");
        }

    }

    public void writeDates() {
        dates.writeCalendarToFile();
        audit("scriereCalendar");
    }

    public void locuriLibere() {
        System.out.println("Nume spectacol: ");
        String numeSpectacol = new Scanner(System.in).next();
        if (!spectacole.existSpectacol(numeSpectacol)) throw new NotExistsException("Nu exista acest spectacol");
        else {
            locuriRezervate.locuriLibere(numeSpectacol);
            audit("afisareLocuriLibere");
        }

    }

    public void spectatori() {
        System.out.println("Nume spectacol: ");
        String numeSpectacol = new Scanner(System.in).next();
        if (!spectacole.existSpectacol(numeSpectacol)) throw new NotExistsException("Nu exista acest spectacol");
        else {
            locuriRezervate.spectatori(numeSpectacol);
            audit("afisareSpectatori");
        }
    }

    public void printSali() {
        sali.printSali();
        audit("afisareSali");
    }

    public void anulareBilet(Persoana pers) {
        System.out.println("Nume spectacol: ");
        String numeSpectacol = new Scanner(System.in).next();
        if (!spectacole.existSpectacol(numeSpectacol)) throw new NotExistsException("Nu exista acest spectacol");
        else {
            locuriRezervate.anulareBilete(pers.getCnp(), numeSpectacol);
            audit("anulareBilete");
        }
    }

    public void printBilet(Persoana pers) {
        System.out.println("Nume spectacol: ");
        String numeSpectacol = new Scanner(System.in).next();
        if (!spectacole.existSpectacol(numeSpectacol)) throw new NotExistsException("Nu exista acest spectacol");
        else {
            locuriRezervate.printareBilet(pers.getCnp(), numeSpectacol);
            audit("printareBilete");
        }
    }

    public void cumparareBilete(Persoana pers) {
        System.out.println("Nume spectacol: ");
        String numeSpectacol = new Scanner(System.in).next();
        if (!spectacole.existSpectacol(numeSpectacol)) throw new NotExistsException("Nu exista acest spectacol");
        else {
            locuriRezervate.cumparareBilet(pers.getCnp(), numeSpectacol);
            audit("cumparareBilete");
        }
    }

    public void updateSala() {
        System.out.println("Ce sala vreti sa modificati?");
        String numeVechi = new Scanner(System.in).next();
        System.out.println("Cum vreti sa redenumiti sala?");
        String numeNou = new Scanner(System.in).next();
        sali.updateSala(numeNou, numeVechi);
        spectacole.modificareSala(numeNou, numeVechi);
        audit("updateSala");
    }

    public void deleteSala() {
        System.out.println("Ce sala vreti sa eliminati");
        String nume = new Scanner(System.in).next();
        sali.deleteSala(nume);
        List<String> spec = spectacole.salaSpectacole(nume);
        for (String numeSpectacol : spec) {
            spectacole.anulareSpectacol(numeSpectacol);
            locuriRezervate.anulareSpectacol(numeSpectacol);
            dates.delete(numeSpectacol);
        }
        audit("deleteSala");
    }

    public void anulareSpectacol() {
        System.out.println("Nume spectacol: ");
        String numeSpectacol = new Scanner(System.in).next();
        spectacole.anulareSpectacol(numeSpectacol);
        locuriRezervate.anulareSpectacol(numeSpectacol);
        dates.delete(numeSpectacol);
        locuriRezervate.anulareSpectacol(numeSpectacol);
        audit("Anulare spectacol");
    }

    public void profitAn() {
        System.out.println("An: ");
        int an = new Scanner(System.in).nextInt();
        double profit = dates.profitAn(an);
        if (profit > 0)
            System.out.println("In anul " + an + " a fost un profit de " + profit + " lei.");
        else
            System.out.println("Nu avem profit pentru anul " + an);
        audit("anulareProfitAn");
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
