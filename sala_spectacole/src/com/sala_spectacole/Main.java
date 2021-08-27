package com.sala_spectacole;

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
                case CLIENT -> {
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
                case ADMINISTRATOR -> {
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
            }
            System.out.println("1.Client\n2.Administrator\n3.Exit");
            input = new Scanner(System.in).nextInt();
        }
        organizare.writeDates();
    }
}
