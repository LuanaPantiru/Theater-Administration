package com.sala_spectacole;

import java.awt.desktop.SystemSleepEvent;
import java.util.*;

public class OrganizareSpectacole {
    private List<Spectacol> spectacole = new ArrayList<>();
    private List<Sala> sali = new ArrayList<>();

    public List<Spectacol> getSpectacole() {
        return spectacole;
    }

    public List<Sala> getSali() {
        return sali;
    }

    public void print_sali()
    {
        for (Sala s : sali)
            System.out.println(s.getNume());
        System.out.println("Vreti sa vizualizati o sala? da/nu");
        Scanner input = new Scanner(System.in);
        if (input.next().equals("da"))
        {
            System.out.println("Nume sala: ");
            input = new Scanner(System.in);
            Sala s = findsala(input.next());
            print_sala(s);
        }
    }
    public void addsala()
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
    public Sala findsala(String nume)
    {
        for(Sala s : sali)
        {
            if(s.getNume().equals(nume))
                return s;
        }
        return null;
    }
    public boolean salalibera(String nume,Integer zi,Integer luna, Integer an){
        for(Spectacol s : spectacole)
            if(s.getNume_spectacol().equals(nume) && s.getZi().equals(zi) && s.getLuna().equals(luna) && s.getAn().equals(an))
                return true;
            return false;
    }
    public void addspectacol()
    {
        System.out.println("Nume sala: ");
        Scanner input = new Scanner(System.in);
        String nume = input.next();
        Sala s = findsala(nume);
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
                if(salalibera(nume,zi,luna,an) == false)
                {
                    Sala sala = new Sala(nume,s.getLocuri_categoria1(),s.getRanduri_categoria1(),s.getLocuri_categoria2(),s.getRanduri_categoria2(),s.getLocuri_loja(),s.getRanduri_loja(),s.getLocuri_balcon(),s.getRanduri_balcon());
                    Spectacol spec = new Spectacol(numes,sala,pret,zi,luna,an);
                    spectacole.add(spec);
                }
                else System.out.println("Sala indisponibila in perioada dorita");
        }
        else
        {
            System.out.println("Sala incorecta");
        }
        Collections.sort(spectacole);
    }
    public void print_sala(Sala sala)
    {
        System.out.println(sala.getNume());
        Map<String,List<Loc_Categoria1>> s = sala.getAsezare();
        for (String i: s.keySet())
        {
            System.out.print(i+" ");
            List<Loc_Categoria1> c = s.get(i);
            String rand = "R1";
            boolean ok = false;
            for (Loc_Categoria1 j : c)
            {
                if( ok ==false)
                {
                    System.out.print("(pret : "+j.getPret()+" lei)\n");
                    ok = true;
                }
                String r = j.getNr_rand();
                if (r.compareTo(rand)==0)
                    System.out.print(j.getNr_loc()+" ");
                else
                {
                    System.out.print("\n"+j.getNr_loc()+" ");
                    rand = r;
                }
            }
            System.out.print("\n");
        }
    }
    public void print_spectacole()
    {
        for (Spectacol s : spectacole)
        {
            System.out.println(s.getNume_spectacol()+", data:"+s.getZi()+"."+s.getLuna()+"."+s.getAn());
        }
    }
    public Spectacol find_spectacol(String nume)
    {
        for (Spectacol s : spectacole)
        {
            if (s.getNume_spectacol().equals(nume))
                return s;
        }
        return null;
    }
    public void anularebilet(Spectacol s, Persoana p)
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
    public void print_bilet(Spectacol s, Persoana p)
    {
        System.out.println(p.getNume()+" "+p.getPrenume());
        List<LocRezervat> lr = s.getRezervat();
        for (LocRezervat l : lr)
        {
            if(l.getP().getPrenume().equals(p.getPrenume()) && l.getP().getNume().equals(p.getNume()))
            {
                String cls = l.getL().getClass().getSimpleName();
                System.out.println(cls+": "+l.getL().getNr_rand()+" "+l.getL().getNr_loc());
            }
        }
    }
    public void profit(Spectacol s)
    {
        double profit = 0;
        List<LocRezervat> lr = s.getRezervat();
        for(LocRezervat l : lr)
        {
            profit += l.getL().getPret();
        }
        if (profit == 0)
        {
            System.out.print("Momentan nu s-a vandut niciun bilet pentru spectacolul "+"'"+s.getNume_spectacol()+"'.");
        }
        else
            System.out.print("Profitul spectacolului "+"'"+s.getNume_spectacol()+"' este: "+profit+" lei\n");
    }
    public void locuri_libere(Spectacol s)
    {
        Map<String,List<Loc_Categoria1>> locuri = s.getS().getAsezare();
        List<LocRezervat> locuri_rez = s.getRezervat();
        for (LocRezervat lr : locuri_rez)
        {
            for(String categorie : locuri.keySet())
            {
                String clas = lr.getL().getClass().getName();
                if(clas.indexOf(categorie) >= 0)
                {
                    List<Loc_Categoria1> l =locuri.get(categorie);
                    for(Loc_Categoria1 c1 : l)
                    {
                        if(c1.getNr_loc() == lr.getL().getNr_loc() && c1.getNr_rand().equals(lr.getL().getNr_rand()))
                        {
                            c1.setNr_loc(0);
                            break;
                        }
                    }
                }
            }
        }
        print_sala(s.getS());
        for(String categorie : locuri.keySet())
        {
            List<Loc_Categoria1> l = locuri.get(categorie);
            Integer loc =1;
            Integer rand = 1;
            for(Loc_Categoria1 c1 : l)
            {
                if(!c1.getNr_rand().equals("R"+rand))
                {
                    rand += 1;
                    loc = 1;
                }
                if(c1.getNr_loc()==0)
                    c1.setNr_loc(loc);
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
    public void cumparare_bilete(Spectacol s, Persoana p)
    {
        Integer nr_locuri;
        System.out.println("Cate locuri doriti?");
        nr_locuri = new Scanner(System.in).nextInt();
        System.out.println("Vreti sa vedeti locurile libere? da/nu");
        String mesaj = new Scanner(System.in).next();
        if (mesaj.equals("da"))
            locuri_libere(s);
        for (Integer i=0;i<nr_locuri;i++)
        {
            System.out.println("Ce tip de loc doriti? Loc_Categoria1/Loc_Categoria2/Loc_Loja/Loc_Balcon");
            Scanner input = new Scanner(System.in);
            String categorie = input.next();
            System.out.println("Numar rand: (ex: R1)");
            input =new Scanner(System.in);
            String rand = input.next();
            System.out.println("Numar loc: ");
            input = new Scanner(System.in);
            Integer loc = input.nextInt();
            List<Loc_Categoria1> zona = s.getS().categorie(categorie);
            for (Loc_Categoria1 z : zona)
            {
                if(z.getNr_loc()== loc && z.getNr_rand().equals(rand))
                {
                    LocRezervat l = new LocRezervat(z,p,z.getPret());
                    s.addbilet(l);
                }
            }

        }

    }

}
