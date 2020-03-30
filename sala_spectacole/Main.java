package com.sala_spectacole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        OrganizareSpectacole os = new OrganizareSpectacole();
        System.out.println("1.Client\n2.Administrator\n3.Exit");
        Scanner input = new Scanner(System.in);
        Integer alegere1 = input.nextInt();
        while(alegere1 != 3) {
            if (alegere1 == 1)
            {
                System.out.println("Bun venit drag client! Vrem sa te cunoastem.");
                System.out.println("Nume :");
                input = new Scanner(System.in);
                String nume = input.next();
                System.out.println("Prenume :");
                input = new Scanner(System.in);
                String prenume = input.next();
                Persoana p = new Persoana(nume,prenume);
                if (os.getSpectacole().isEmpty())
                {
                    System.out.println("Ne pare rau... Nu avem spectacole");
                }
                else
                {
                    System.out.println("1.Vizualizare spectacole\n2.Vizualizare locuri libere la un spectacol\n3.Cumparare bilete\n4.Anulare bilete\n5.Printare bilete\n6.Exit");
                    input = new Scanner(System.in);
                    Integer alegere = input.nextInt();
                    while( alegere != 6)
                    {
                        if(alegere == 1)
                            os.print_spectacole();
                        else if(alegere == 2)
                        {
                            System.out.println("Nume spectacol: ");
                            input = new Scanner(System.in);
                            String spectacol = input.next();
                            os.locuri_libere(os.find_spectacol(spectacol));
                        }
                        else if(alegere == 3)
                        {
                            System.out.println("La ce spectacol doriti bilet?");
                            input = new Scanner(System.in);
                            String s = input.next();
                            Spectacol spectacol = os.find_spectacol(s);
                            os.cumparare_bilete(spectacol,p);
                        }
                        else if(alegere == 4) {
                            System.out.println("Nume spectacol");
                            String s = new Scanner(System.in).next();
                            Spectacol spectacol = os.find_spectacol(s);
                            os.anularebilet(spectacol, p);
                        }
                        else
                        {
                            System.out.println("Nume spectacol");
                            String s = new Scanner(System.in).next();
                            Spectacol spectacol = os.find_spectacol(s);
                            os.print_bilet(spectacol, p);
                        }
                        System.out.println("1.Vizualizare spectacole\n2.Vizualizare locuri libere la un spectacol\n3.Cumparare bilete\n4.Anulare bilete\n5.Printare bilete\n6.Exit");
                        input = new Scanner(System.in);
                        alegere = input.nextInt();

                    }
                }
            }
            else
            {
                System.out.println("Bun venit");
                if (os.getSali().isEmpty()) {
                    System.out.println("Trebuie adaugate sali");
                    os.addsala();
                }
                if (os.getSpectacole().isEmpty()) {
                    System.out.println("Trebuie adaugate spectacole");
                    os.addspectacol();
                }
                System.out.println("1.Adauga sala\n2.Adauga spectacol\n3.Afiseaza spectacole\n4.Afiseaza sali\n5.Lista spectatori la un spectacol\n6.Profitul pentru un spectacol\n7.Exit");
                input = new Scanner(System.in);
                Integer alegere = input.nextInt();
                while(alegere != 7)
                {
                    if(alegere == 1)
                        os.addsala();
                    else if(alegere == 2)
                        os.addspectacol();
                    else if(alegere == 3)
                        os.print_spectacole();
                    else if (alegere == 4)
                        os.print_sali();
                    else if(alegere ==5)
                    {
                        System.out.println("Nume spectacol: ");
                        String nume = new Scanner(System.in).next();
                        Spectacol spectacol = os.find_spectacol(nume);
                        os.spectatori(spectacol);
                    }
                    else if(alegere == 6)
                    {
                        System.out.println("Nume spectacol: ");
                        String nume = new Scanner(System.in).next();
                        Spectacol spectacol = os.find_spectacol(nume);
                        os.profit(spectacol);
                    }
                    System.out.println("1.Adauga sala\n2.Adauga spectacol\n3.Afiseaza spectacole\n4.Afiseaza sali\n5.Lista spectatori la un spectacol\n6.Profitul pentru un spectacol\n7.Exit");
                    input = new Scanner(System.in);
                    alegere = input.nextInt();

                }

            }
            System.out.println("1.Client\n2.Administrator\n3.Exit");
            input = new Scanner(System.in);
            alegere1 = input.nextInt();
        }
    }
}
