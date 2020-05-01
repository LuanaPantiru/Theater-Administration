package com.sala_spectacole.service;

import com.sala_spectacole.domain.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LocRezervatService {

    private static LocRezervatService instance = null;

    public static LocRezervatService getInstance() {
        if (instance == null)
            instance = new LocRezervatService();
        return instance;
    }

    public void readRezervareFromFile(OrganizareSpectacole organizare) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("locrezervare.txt"))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] dataFields = currentLine.split(",");
                LocCategoria1 loc;
                String spectacol = dataFields[0];
                switch (dataFields[1]) {
                    case "LocCategoria2":
                        loc = new LocCategoria2(Integer.parseInt(dataFields[2]), dataFields[3]);
                        break;
                    case "LocBalcon":
                        loc = new LocBalcon(Integer.parseInt(dataFields[2]), dataFields[3]);
                        break;
                    case "LocLoja":
                        loc = new LocLoja(Integer.parseInt(dataFields[2]), dataFields[3]);
                        break;
                    default:
                        loc = new LocCategoria1(Integer.parseInt(dataFields[2]), dataFields[3]);
                }
                LocRezervat locRezervat = new LocRezervat(loc, new Persoana(dataFields[4], dataFields[5]), Double.parseDouble(dataFields[6]));
                Spectacol s = organizare.findSpectacol(spectacol);
                if (s != null)
                    s.addBilet(locRezervat);
            }
        } catch (IOException e) {
            System.out.println("Could not read data from file: " + e.getMessage());
            return;
        }

        System.out.println("Successfully read locrezervare.txt");
    }

    public void writeRezervareToFile(OrganizareSpectacole organizare) {
        List<Spectacol> spectacole = new ArrayList<>(organizare.getSpectacoleInchise());
        spectacole.addAll(organizare.getSpectacoleActive());
        int nr = 0;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("locrezervare.txt"))) {
            for (Spectacol spect : spectacole) {
                List<LocRezervat> rezervari = spect.getLocuriRezervateRezervat();
                for (LocRezervat rezervat : rezervari) {
                    bufferedWriter.write(spect.getNumeSpectacol() + "," + rezervat.getLoc().getClass().getSimpleName() + "," + rezervat.getLoc().getNrLoc() + "," + rezervat.getLoc().getNrRand() + "," + rezervat.getPersoana().getNume() + "," + rezervat.getPersoana().getPrenume() + "," + rezervat.getPret());
                    bufferedWriter.newLine();
                    nr++;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully wrote " + nr + " sits!");
    }
}


