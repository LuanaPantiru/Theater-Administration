package com.sala_spectacole.service;

import com.sala_spectacole.domain.*;
import com.sala_spectacole.domain.Calendar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class OrganizareSpectacole {
    private static List<Spectacol> spectacoleActive = new ArrayList<>();
    private static List<Spectacol> spectacoleInchise = new ArrayList<>();
    private static List<Sala> sali = new ArrayList<>();
    private static List<Calendar> calendar = new ArrayList<>();

    public static List<Sala> getSali() { return sali; }
    public static List<Spectacol> getSpectacoleActive() { return spectacoleActive; }
    public static List<Spectacol> getSpectacoleInchise() { return spectacoleInchise; }
    public static List<Calendar> getCalendar() { return calendar; }

    public static void setCalendar(List<Calendar> calendar) {
        OrganizareSpectacole.calendar = calendar;
    }

    public static void setSpectacoleActive(List<Spectacol> spectacoleActive) {
        OrganizareSpectacole.spectacoleActive = spectacoleActive;
    }

    public static void setSpectacoleInchise(List<Spectacol> spectacoleInchise) {
        OrganizareSpectacole.spectacoleInchise = spectacoleInchise;
    }

    //actiuni legate de spectacole
    public void addSpectacol()
    {
        while(true){
            System.out.println("Nume sala: ");
            Scanner input = new Scanner(System.in);
            String nume = input.next();
            Sala s = findSala(nume);
            if (s != null)
            {
                System.out.println("Nume spectacol: ");
                input = new Scanner(System.in);
                String numes = input.next();
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
                if(!salaLibera(nume,zi,luna,an))
                {
                    Sala sala = new Sala(nume,s.getLocuriCategoria1(),s.getRanduriCategoria1(),s.getLocuriCategoria2(),s.getRanduriCategoria2(),s.getLocuriLoja(),s.getRanduriLoja(),s.getLocuriBalcon(),s.getRanduriBalcon());
                    Spectacol spec = new Spectacol(numes,sala,pret,zi,luna,an);
                    Calendar date = new Calendar(zi,luna,an,numes);
                    spectacoleActive.add(spec);
                    calendar.add(date);
                    Collections.sort(spectacoleActive);
                    Collections.sort(calendar);
                    break;
                }
                else System.out.println("Sala indisponibila in perioada dorita");
            }
            else
            {
                System.out.println("Sala incorecta");
            }

        }

    }
    public void printSpectacole()
    {
        for (Spectacol s : spectacoleActive)
        {
            System.out.println(s.getNumeSpectacol()+", data:"+s.getZi()+"."+s.getLuna()+"."+s.getAn());
        }
    }
    public static Spectacol findSpectacol(String nume)
    {
        for (Spectacol s : spectacoleActive)
        {
            if (s.getNumeSpectacol().equals(nume))
                return s;
        }
        for (Spectacol s: spectacoleInchise)
            if(s.getNumeSpectacol().equals(nume))
                return s;
        return null;
    }
    public double profit(Spectacol s)
    {
        double profit = 0;
        List<LocRezervat> lr = s.getRezervat();
        for(LocRezervat l : lr)
        {
            profit += l.getL().getPret();
        }
        return profit;
    }
    public void locuriLibere(Spectacol s)
    {
        Map<String,List<LocCategoria1>> locuri = s.getS().getAsezare();
        List<LocRezervat> locuri_rez = s.getRezervat();
        for (LocRezervat lr : locuri_rez)
        {
            for(String categorie : locuri.keySet())
            {
                String clas = lr.getL().getClass().getName();
                if(clas.contains(categorie))
                {
                    List<LocCategoria1> l =locuri.get(categorie);
                    for(LocCategoria1 c1 : l)
                    {
                        if(c1.getNrLoc().equals(lr.getL().getNrLoc()) && c1.getNrRand().equals(lr.getL().getNrRand()))
                        {
                            c1.setNrLoc(0);
                            break;
                        }
                    }
                }
            }
        }
        printSala(s.getS());
        for(String categorie : locuri.keySet())
        {
            List<LocCategoria1> l = locuri.get(categorie);
            int loc =1;
            int rand = 1;
            for(LocCategoria1 c1 : l)
            {
                if(!c1.getNrRand().equals("R"+rand))
                {
                    rand += 1;
                    loc = 1;
                }
                if(c1.getNrLoc()==0)
                    c1.setNrLoc(loc);
                loc += 1;
            }
        }
    }
    public void spectatori(Spectacol s)
    {
        List<LocRezervat> lr = s.getRezervat();
        Set<Persoana> spectatori = new HashSet<>();
        for(LocRezervat l : lr)
            spectatori.add(l.getP());
        for(Persoana p : spectatori)
            System.out.println(p.getNume()+" "+p.getPrenume());
    }
    //actiuni legate de sali


    public void printSali()
    {
        for (Sala s : sali)
            System.out.println(s.getNume());
        System.out.println("Vreti sa vizualizati o sala? da/nu");
        Scanner input = new Scanner(System.in);
        if (input.next().equals("da"))
        {
            System.out.println("Nume sala: ");
            input = new Scanner(System.in);
            Sala s = findSala(input.next());
            if(s != null)
                printSala(s);
            else System.out.println("Nu s-a gasit sala.");
        }
    }
    public void addSala()
    {
        System.out.println("Nume sala: ");
        Scanner input = new Scanner(System.in);
        String nume = input.next();
        System.out.println("Numar locuri categoria1: ");
        input = new Scanner(System.in);
        Integer lc1 = input.nextInt();
        System.out.println("Numar randuri categoria1: ");
        input = new Scanner(System.in);
        Integer rc1 = input.nextInt();
        System.out.println("Numar locuri categoria2: ");
        input = new Scanner(System.in);
        Integer lc2 = input.nextInt();
        System.out.println("Numar randuri categoria2: ");
        input = new Scanner(System.in);
        Integer rc2 = input.nextInt();
        System.out.println("Numar locuri loja: ");
        input = new Scanner(System.in);
        Integer ll = input.nextInt();
        System.out.println("Numar randuri loja: ");
        input = new Scanner(System.in);
        Integer rl = input.nextInt();
        System.out.println("Numar locuri balcon: ");
        input = new Scanner(System.in);
        Integer lb = input.nextInt();
        System.out.println("Numar randuri balcon: ");
        input = new Scanner(System.in);
        Integer rb = input.nextInt();
        Sala s = new Sala(nume,lc1,rc1,lc2,rc2,ll,rl,lb,rb);
        sali.add(s);
    }
    public static Sala findSala(String nume)
    {
        for(Sala s : sali)
        {
            if(s.getNume().equals(nume))
                return s;
        }
        return null;
    }
    public boolean salaLibera(String nume,Integer zi,Integer luna, Integer an){
        for(Spectacol s : spectacoleActive)
            if(s.getNumeSpectacol().equals(nume) && s.getZi().equals(zi) && s.getLuna().equals(luna) && s.getAn().equals(an))
                return true;
            return false;
    }

    public void printSala(Sala sala)
    {
        System.out.println(sala.getNume());
        Map<String,List<LocCategoria1>> s = sala.getAsezare();
        for (String i: s.keySet())
        {
            System.out.print(i+" ");
            List<LocCategoria1> c = s.get(i);
            String rand = "R1";
            boolean ok = false;
            for (LocCategoria1 j : c)
            {
                if(!ok)
                {
                    System.out.print("(pret : "+j.getPret()+" lei)\n");
                    ok = true;
                }
                String r = j.getNrRand();
                if (r.compareTo(rand)==0)
                    System.out.print(j.getNrLoc()+" ");
                else
                {
                    System.out.print("\n"+j.getNrLoc()+" ");
                    rand = r;
                }
            }
            System.out.print("\n");
        }
    }
    //actiuni legate de bilete
    public void anulareBilet(Spectacol s, Persoana p)
    {
        List<LocRezervat> lr = s.getRezervat();
        List<Integer> index = new ArrayList<>();
        for (int i = 0;i<lr.size();i++)
        {
            LocRezervat l = lr.get(i);
            if(l.getP().getNume().equals(p.getNume()) && l.getP().getPrenume().equals(p.getPrenume()))
            {
                lr.remove(i);
                i -= 1;
            }
        }
    }
    public void printBilet(Spectacol s, Persoana p)
    {
        System.out.println(p.getNume()+" "+p.getPrenume());
        List<LocRezervat> lr = s.getRezervat();
        for (LocRezervat l : lr)
        {
            if(l.getP().getPrenume().equals(p.getPrenume()) && l.getP().getNume().equals(p.getNume()))
            {
                String cls = l.getL().getClass().getSimpleName();
                System.out.println(cls+": "+l.getL().getNrRand()+" "+l.getL().getNrLoc());
            }
        }
    }


    public void cumparareBilete(Spectacol s, Persoana p)
    {
        System.out.println("Cate locuri doriti?");
        int nr_locuri = new Scanner(System.in).nextInt();
        System.out.println("Vreti sa vedeti locurile libere? da/nu");
        String mesaj = new Scanner(System.in).next();
        if (mesaj.equals("da"))
            locuriLibere(s);
        for (int i=0;i<nr_locuri;i++)
        {
            System.out.println("Ce tip de loc doriti? LocCategoria1/LocCategoria2/LocLoja/LocBalcon");
            Scanner input = new Scanner(System.in);
            String categorie = input.next();
            System.out.println("Numar rand: (ex: R1)");
            input =new Scanner(System.in);
            String rand = input.next();
            System.out.println("Numar loc: ");
            input = new Scanner(System.in);
            Integer loc = input.nextInt();
            List<LocCategoria1> zona = s.getS().categorie(categorie);
            for (LocCategoria1 z : zona)
            {
                if(z.getNrLoc().equals(loc) && z.getNrRand().equals(rand))
                {
                    LocRezervat l = new LocRezervat(z,p,z.getPret());
                    s.addBilet(l);
                }
            }

        }

    }
    //actiuni legate de calendar
    public static void cancelSpectacole()
    {
        LocalDate data = LocalDate.now();
        int zi = data.getDayOfMonth();
        int luna = data.getMonthValue();
        int an = data.getYear();
        List<Spectacol> spec =new ArrayList<>();
        for(Calendar c: calendar)
        {
            if (c.beforeDate(zi,luna,an))
            {
                Spectacol s = findSpectacol(c.getNumeSpectacol());
                spectacoleActive.remove(s);
                spec.add(s);
            }
        }
        OrganizareSpectacole.setSpectacoleInchise(spec);
    }
    public double profitAn(int an)
    {
        double suma = 0;
        for(Calendar date: calendar) {
            if (date.getAn() == an) {
                Spectacol spect = findSpectacol(date.getNumeSpectacol());
                if (spect != null)
                    suma += profit(spect);
            }
            else if (date.getAn()>an)
                break;
        }
        return suma;
    }
    public void audit(String actiune)
    {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("istoric.txt",true))){
            LocalDate data = LocalDate.now();
            bufferedWriter.write(actiune+","+data);
            bufferedWriter.newLine();

        }catch (IOException e){
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully wrote action.");
    }
}
