package com.sala_spectacole;

import com.sala_spectacole.domain.LocRezervat;
import com.sala_spectacole.domain.Persoana;
import com.sala_spectacole.domain.Sala;
import com.sala_spectacole.domain.Spectacol;
import com.sala_spectacole.service.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        OrganizareSpectacole organizare = new OrganizareSpectacole();
        SalaService salaService = SalaService.getInstance();
        SpectacolService spectacolService = SpectacolService.getInstance();
        LocRezervatService locRezervatService = LocRezervatService.getInstance();
        CalendarService calendarService = CalendarService.getInstance();
        salaService.readSalaFromFile(organizare);
        spectacolService.readSpectacolFromFile(organizare);
        locRezervatService.readRezervareFromFile(organizare);package com.sala_spectacole;

import com.sala_spectacole.domain.Persoana;
import com.sala_spectacole.service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public enum LogIn {
        CLIENT,
        ADMINISTRATOR;
    }

    public enum Client {
        VIZUALIZARE_SPECTACOLE,
        LOCURI_LIBERE,
        CUMPARARE_BILETE,
        ANULARE_BILETE,
        PRINTARE_BILETE,
        UPDATE_NUME,
        ANULARE_CONT;
    }

    public enum Administrator {
        ADAUGA_SALA,
        ADAUGA_SPECTACOL,
        AFISARE_SPECTACOLE,
        AFISARE_SALI,
        LISTA_SPECTATORI,
        PROFIT_SPECTACOL,
        PROFIT_AN,
        ANULARE_SPECTACOL,
        ELIMINARE_SALA,
        MODIFICARE_NUME_SALA;
    }

    public static void main(String[] args) {
        OrganizareSpectacole organizare = new OrganizareSpectacole();
        List<String> logIn = new ArrayList<>(Arrays.asList("CLIENT", "ADMINISTRATOR"));
        List<String> client = new ArrayList<>(Arrays.asList("VIZUALIZARE_SPECTACOLE", "LOCURI_LIBERE", "CUMPARARE_BILETE",
                "ANULARE_BILETE", "PRINTARE_BILETE", "UPDATE_NUME", "ANULARE_CONT"));
        List<String> administrator = new ArrayList<>(Arrays.asList("ADAUGA_SALA", "ADAUGA_SPECTACOL", "AFISARE_SPECTACOLE",
                "AFISARE_SALI", "LISTA_SPECTATORI", "PROFIT_SPECTACOL", "PROFIT_AN", "ANULARE_SPECTACOL", "ELIMINARE_SALA",
                "MODIFICARE_NUME_SALA"));
        System.out.println("1.Client\n2.Administrator\n3.Exit");
        int input = new Scanner(System.in).nextInt();
        while (input <= logIn.size()) {
            LogIn alegere = LogIn.valueOf(logIn.get(input - 1));
            switch (alegere) {
                case CLIENT: {
                    Persoana pers = organizare.autentificare();
                    System.out.println("1.Vizualizare spectacole\n2.Vizualizare locuri libere la un spectacol\n3.Cumparare bilete\n4.Anulare bilete\n5.Printare bilete\n6.Modificare nume\n7.Stergere cont\n8.Exit");
                    int inputClient = new Scanner(System.in).nextInt();
                    while (inputClient <= client.size()) {
                        Client alegereClient = Client.valueOf(client.get(inputClient - 1));
                        switch (alegereClient) {
                            case VIZUALIZARE_SPECTACOLE: {
                                organizare.printSpectacole();
                            }
                            break;
                            case LOCURI_LIBERE: {
                                organizare.locuriLibere();
                            }
                            break;
                            case CUMPARARE_BILETE: {
                                organizare.cumparareBilete(pers);
                            }
                            break;
                            case ANULARE_BILETE: {
                                organizare.anulareBilet(pers);
                            }
                            break;
                            case PRINTARE_BILETE: {
                                organizare.printBilet(pers);
                            }
                            break;
                            case UPDATE_NUME: {
                                organizare.updateNume(pers);
                            }
                            break;
                            case ANULARE_CONT: {
                                organizare.anulareCont(pers);
                            }
                            break;
                        }
                        System.out.println("1.Vizualizare spectacole\n2.Vizualizare locuri libere la un spectacol\n3.Cumparare bilete\n4.Anulare bilete\n5.Printare bilete\n6.Modificare nume\n7.Stergere cont\n8.Exit");
                        inputClient = new Scanner(System.in).nextInt();
                    }
                }
                break;
                case ADMINISTRATOR: {
                    System.out.println("Bun venit");
                    System.out.println("1.Adauga sala\n2.Adauga spectacol\n3.Afiseaza spectacole\n4.Afiseaza sali\n5.Lista spectatori la un spectacol\n6.Profitul pentru un spectacol\n7.Calculare profit pe an\n8.Anulare spectacol\n9.Eliminare sala\n10.Modificare numele salii\n11.Exit");
                    int inputAdministrator = new Scanner(System.in).nextInt();
                    while (inputAdministrator <= administrator.size()) {
                        Administrator alegereAdministrator = Administrator.valueOf(administrator.get(inputAdministrator - 1));
                        switch (alegereAdministrator) {
                            case ADAUGA_SALA: {
                                organizare.addSala();
                            }
                            break;
                            case ADAUGA_SPECTACOL: {
                                organizare.addSpectacol();
                            }
                            break;
                            case AFISARE_SPECTACOLE: {
                                organizare.printSpectacole();
                            }
                            break;
                            case AFISARE_SALI: {
                                organizare.printSali();
                            }
                            break;
                            case LISTA_SPECTATORI: {
                                organizare.spectatori();
                            }
                            break;
                            case PROFIT_SPECTACOL: {
                                organizare.profit();
                            }
                            break;
                            case PROFIT_AN: {
                                organizare.profitAn();
                            }
                            break;
                            case ANULARE_SPECTACOL: {
                                organizare.anulareSpectacol();
                            }
                            break;
                            case ELIMINARE_SALA: {
                                organizare.deleteSala();
                            }
                            break;
                            case MODIFICARE_NUME_SALA: {
                                organizare.updateSala();
                            }
                            break;
                        }
                        System.out.println("1.Adauga sala\n2.Adauga spectacol\n3.Afiseaza spectacole\n4.Afiseaza sali\n5.Lista spectatori la un spectacol\n6.Profitul pentru un spectacol\n7.Calculare profit pe an\n8.Anulare spectacol\n9.Eliminare sala\n10.Modificare numele salii\n11.Exit");
                        inputAdministrator = new Scanner(System.in).nextInt();
                    }
                }
                break;
            }
            System.out.println("1.Client\n2.Administrator\n3.Exit");
            input = new Scanner(System.in).nextInt();
        }
        organizare.writeDates();
    }
}
        calendarService.readCalendarFromFile(organizare);
        organizare.cancelSpectacole();
        System.out.println("1.Client\n2.Administrator\n3.Exit");
        Scanner input = new Scanner(System.in);
        int alegere1 = input.nextInt();
        while (alegere1 != 3) {
            if (alegere1 == 1) {
                System.out.println("Bun venit drag client! Vrem sa te cunoastem.");
                System.out.println("Nume :");
                input = new Scanner(System.in);
                String nume = input.next();
                System.out.println("Prenume :");
                input = new Scanner(System.in);
                String prenume = input.next();
                Persoana p = new Persoana(nume, prenume);
                if (organizare.getSpectacoleActive().isEmpty()) {
                    System.out.println("Ne pare rau... Nu avem spectacole");
                } else {
                    System.out.println("1.Vizualizare spectacole\n2.Vizualizare locuri libere la un spectacol\n3.Cumparare bilete\n4.Anulare bilete\n5.Printare bilete\n6.Exit");
                    input = new Scanner(System.in);
                    int alegere = input.nextInt();
                    while (alegere != 6) {
                        switch (alegere) {
                            case 1: {
                                organizare.printSpectacole();
                                organizare.audit("vizualizareSpectacole");
                            }
                            break;
                            case 2: {
                                while (true) {
                                    System.out.println("Nume spectacol: ");
                                    input = new Scanner(System.in);
                                    String spectacol = input.next();
                                    Spectacol spect = organizare.findSpectacol(spectacol);
                                    if (spect != null) {
                                        organizare.locuriLibere(spect);
                                        organizare.audit("vizualizreLocuriLibere");
                                        break;
                                    } else {
                                        System.out.println("Nu exista acest spectacol");
                                    }
                                }
                            }
                            break;
                            case 3: {
                                while (true) {
                                    System.out.println("La ce spectacol doriti bilet?");
                                    input = new Scanner(System.in);
                                    String s = input.next();
                                    Spectacol spectacol = organizare.findSpectacol(s);
                                    if (spectacol != null) {
                                        organizare.cumparareBilete(spectacol, p);
                                        organizare.audit("cumparareBilet");
                                        break;
                                    } else
                                        System.out.println("Nu exista acest spectacol");
                                }
                            }
                            break;
                            case 4: {
                                while (true) {
                                    System.out.println("Nume spectacol");
                                    String s = new Scanner(System.in).next();
                                    Spectacol spectacol = organizare.findSpectacol(s);
                                    if (spectacol != null) {
                                        organizare.anulareBilet(spectacol, p);
                                        organizare.audit("anulareBilet");
                                        break;
                                    } else
                                        System.out.println("Nu exista acest spectacol");

                                }
                            }
                            break;
                            case 5: {
                                while (true) {
                                    System.out.println("Nume spectacol");
                                    String s = new Scanner(System.in).next();
                                    Spectacol spectacol = organizare.findSpectacol(s);
                                    if (spectacol != null) {
                                        organizare.printBilet(spectacol, p);
                                        organizare.audit("printareBilet");
                                        break;
                                    } else
                                        System.out.println("Nu exista acest spectacol");
                                }
                            }
                            break;
                        }
                        System.out.println("1.Vizualizare spectacole\n2.Vizualizare locuri libere la un spectacol\n3.Cumparare bilete\n4.Anulare bilete\n5.Printare bilete\n6.Exit");
                        input = new Scanner(System.in);
                        alegere = input.nextInt();
                    }
                }
            } else {
                System.out.println("Bun venit");
                if (organizare.getSali().isEmpty()) {
                    System.out.println("Trebuie adaugate sali");
                    organizare.addSala();
                }
                if (organizare.getSpectacoleActive().isEmpty()) {
                    System.out.println("Trebuie adaugate spectacole");
                    organizare.addSpectacol();
                }
                System.out.println("1.Adauga sala\n2.Adauga spectacol\n3.Afiseaza spectacole\n4.Afiseaza sali\n5.Lista spectatori la un spectacol\n6.Profitul pentru un spectacol\n7.Calculare profit pe an\n8.Exit");
                input = new Scanner(System.in);
                int alegere = input.nextInt();
                while (alegere != 8) {
                    switch (alegere) {
                        case 1: {
                            organizare.addSala();
                            organizare.audit("adaugareSala");
                        }
                        break;
                        case 2: {
                            organizare.addSpectacol();
                            organizare.audit("adaugareSpectacol");
                        }
                        break;
                        case 3: {
                            organizare.printSpectacole();
                            organizare.audit("vizualizareSpectacole");
                        }
                        break;
                        case 4: {
                            organizare.printSali();
                            organizare.audit("vizualizareSali");
                        }
                        break;
                        case 5: {
                            while (true) {
                                System.out.println("Nume spectacol: ");
                                String nume = new Scanner(System.in).next();
                                Spectacol spectacol = organizare.findSpectacol(nume);
                                if (spectacol != null) {
                                    organizare.spectatori(spectacol);
                                    organizare.audit("realizareListaSpectatori");
                                    break;
                                } else
                                    System.out.println("Nu exista acest spectacol");
                            }
                        }
                        break;
                        case 6: {
                            while (true) {
                                System.out.println("Nume spectacol: ");
                                String nume = new Scanner(System.in).next();
                                Spectacol spectacol = organizare.findSpectacol(nume);
                                if (spectacol != null) {
                                    double profit = organizare.profit(spectacol);
                                    if (profit > 0)
                                        System.out.println("Spectacolul " + nume + " are un profit de " + profit + " lei.");
                                    else
                                        System.out.println("Nu avem profit pentru spectacolol " + nume);
                                    organizare.audit("calculareProfit");
                                    break;
                                } else
                                    System.out.println("Nu exista acest spectacol");
                            }
                        }
                        break;
                        case 7: {
                            System.out.println("Anul: ");
                            int an = new Scanner(System.in).nextInt();
                            double profit = organizare.profitAn(an);
                            System.out.println("Profitul pe anul " + an + " este " + profit + " lei.");
                            organizare.audit("calculareProfitAn");
                        }
                        break;
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
        calendarService.writeCalendarToFile(organizare);
        locRezervatService.writeRezervareToFile(organizare);
        spectacolService.writeSpectacolToFile(organizare);
        salaService.writeSalaToFile(organizare);
    }
}
