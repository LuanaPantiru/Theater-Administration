package com.sala_spectacole;

import com.sala_spectacole.domain.Persoana;
import com.sala_spectacole.domain.Sala;
import com.sala_spectacole.domain.Spectacol;
import com.sala_spectacole.service.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        OrganizareSpectacole os = new OrganizareSpectacole();
        SalaService.readSalaFromFile();
        SpectacolService.readSpectacolFromFile();
        LocRezervatService.readRezervareFromFile();
        CalendarService.readCalendarFromFile();
        OrganizareSpectacole.cancelSpectacole();
        System.out.println("1.Client\n2.Administrator\n3.Exit");
        Scanner input = new Scanner(System.in);
        int alegere1 = input.nextInt();
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
                if (OrganizareSpectacole.getSpectacoleActive().isEmpty())
                {
                    System.out.println("Ne pare rau... Nu avem spectacole");
                }
                else
                {
                    System.out.println("1.Vizualizare spectacole\n2.Vizualizare locuri libere la un spectacol\n3.Cumparare bilete\n4.Anulare bilete\n5.Printare bilete\n6.Exit");
                    input = new Scanner(System.in);
                    int alegere = input.nextInt();
                    while(alegere!=6)
                    {
                        switch (alegere){
                            case 1:{
                                os.printSpectacole();
                                os.audit("vizualizareSpectacole");
                            }break;
                            case 2:{
                                while(true)
                                {
                                    System.out.println("Nume spectacol: ");
                                    input = new Scanner(System.in);
                                    String spectacol = input.next();
                                    Spectacol spect = OrganizareSpectacole.findSpectacol(spectacol);
                                    if (spect!=null)
                                    {
                                        os.locuriLibere(spect);
                                        os.audit("vizualizreLocuriLibere");
                                        break;
                                    }
                                    else{
                                        System.out.println("Nu exista acest spectacol");
                                    }
                                }
                            }break;
                            case 3:{
                                while(true){
                                    System.out.println("La ce spectacol doriti bilet?");
                                    input = new Scanner(System.in);
                                    String s = input.next();
                                    Spectacol spectacol = OrganizareSpectacole.findSpectacol(s);
                                    if(spectacol != null){
                                        os.cumparareBilete(spectacol,p);
                                        os.audit("cumparareBilet");
                                        break;
                                    }
                                    else
                                        System.out.println("Nu exista acest spectacol");
                                }
                            }break;
                            case 4:{
                                while(true){
                                    System.out.println("Nume spectacol");
                                    String s = new Scanner(System.in).next();
                                    Spectacol spectacol = OrganizareSpectacole.findSpectacol(s);
                                    if (spectacol!=null){
                                        os.anulareBilet(spectacol, p);
                                        os.audit("anulareBilet");
                                        break;
                                    }
                                    else
                                        System.out.println("Nu exista acest spectacol");

                                }
                            }break;
                            case 5:{
                                while(true){
                                    System.out.println("Nume spectacol");
                                    String s = new Scanner(System.in).next();
                                    Spectacol spectacol = OrganizareSpectacole.findSpectacol(s);
                                    if(spectacol!=null){
                                        os.printBilet(spectacol, p);
                                        os.audit("printareBilet");
                                        break;
                                    }
                                    else
                                        System.out.println("Nu exista acest spectacol");
                                }
                            }break;
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
                if (OrganizareSpectacole.getSali().isEmpty()) {
                    System.out.println("Trebuie adaugate sali");
                    os.addSala();
                }
                if (OrganizareSpectacole.getSpectacoleActive().isEmpty()) {
                    System.out.println("Trebuie adaugate spectacole");
                    os.addSpectacol();
                }
                System.out.println("1.Adauga sala\n2.Adauga spectacol\n3.Afiseaza spectacole\n4.Afiseaza sali\n5.Lista spectatori la un spectacol\n6.Profitul pentru un spectacol\n7.Calculare profit pe an\n8.Exit");
                input = new Scanner(System.in);
                int alegere = input.nextInt();
                while(alegere!=8)
                {
                    switch (alegere){
                        case 1:{
                            os.addSala();
                            os.audit("adaugareSala");
                        }break;
                        case 2:{
                            os.addSpectacol();
                            os.audit("adaugareSpectacol");
                        }break;
                        case 3:{
                            os.printSpectacole();
                            os.audit("vizualizareSpectacole");
                        }break;
                        case 4:{
                            os.printSali();
                            os.audit("vizualizareSali");
                        }break;
                        case 5:{
                            while(true){
                                System.out.println("Nume spectacol: ");
                                String nume = new Scanner(System.in).next();
                                Spectacol spectacol = OrganizareSpectacole.findSpectacol(nume);
                                if (spectacol != null) {
                                    os.spectatori(spectacol);
                                    os.audit("realizareListaSpectatori");
                                    break;
                                }
                                else
                                    System.out.println("Nu exista acest spectacol");
                            }
                        }break;
                        case 6:{
                            while(true){
                                System.out.println("Nume spectacol: ");
                                String nume = new Scanner(System.in).next();
                                Spectacol spectacol = OrganizareSpectacole.findSpectacol(nume);
                                if (spectacol != null)
                                {
                                    double profit = os.profit(spectacol);
                                    if (profit>0)
                                        System.out.println("Spectacolul "+nume+" are un profit de "+ profit+" lei.");
                                    else
                                        System.out.println("Nu avem profit pentru spectacolol "+nume);
                                    os.audit("calculareProfit");
                                    break;
                                }
                                else
                                    System.out.println("Nu exista acest spectacol");
                            }
                        }break;
                        case 7:{
                            System.out.println("Anul: ");
                            int an = new Scanner(System.in).nextInt();
                            double profit = os.profitAn(an);
                            System.out.println("Profitul pe anul "+ an+" este "+profit+" lei.");
                            os.audit("calculareProfitAn");
                        }break;
                    }

                    System.out.println("1.Adauga sala\n2.Adauga spectacol\n3.Afiseaza spectacole\n4.Afiseaza sali\n5.Lista spectatori la un spectacol\n6.Profitul pentru un spectacol\n7.Calculare profit pe an\n8.Exit");
                    input = new Scanner(System.in);
                    alegere = input.nextInt();
                }
            }
            System.out.println("1.Client\n2.Administrator\n3.Exit");
            input = new Scanner(System.in);
            alegere1 = input.nextInt();
        }
        CalendarService.writeCalendarToFile();
        LocRezervatService.writeRezervareToFile();
        SpectacolService.writeSpectacolToFile();
        SalaService.writeSalaToFile();
    }
}
