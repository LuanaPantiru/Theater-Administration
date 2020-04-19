package com.sala_spectacole.service;

import com.sala_spectacole.domain.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LocRezervatService {

        private static LocRezervatService instance = null;
        public static LocRezervatService getInstance()
        {
            if (instance == null)
                instance = new LocRezervatService();
            return instance;
        }
        public static void readRezervareFromFile(){
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader("locrezervare.txt"))){
                String currentLine;
                while((currentLine = bufferedReader.readLine()) != null)
                {
                    String[] dataFields = currentLine.split(",");
                    LocCategoria1 l;
                    String spectacol = dataFields[0];
                    switch (dataFields[1]){
                        case "LocCategoria2":
                            l = new LocCategoria2(Integer.parseInt(dataFields[2]),dataFields[3]);
                            break;
                        case "LocBalcon":
                            l = new LocBalcon(Integer.parseInt(dataFields[2]),dataFields[3]);
                            break;
                        case "LocLoja":
                            l = new LocLoja(Integer.parseInt(dataFields[2]),dataFields[3]);
                            break;
                        default:
                            l = new LocCategoria1(Integer.parseInt(dataFields[2]),dataFields[3]);
                    }
                    LocRezervat loc = new LocRezervat(l,new Persoana(dataFields[4],dataFields[5]),Double.parseDouble(dataFields[6]));
                    Spectacol s = OrganizareSpectacole.findSpectacol(spectacol);
                    if (s!=null)
                        s.addBilet(loc);
                }
            }catch(IOException e){
                System.out.println("Could not read data from file: "+e.getMessage());
                return;
            }

            System.out.println("Successfully read locrezervare.txt");
        }
    public static void writeRezervareToFile()
    {
        List<Spectacol> spectacole = new ArrayList<>(OrganizareSpectacole.getSpectacoleInchise());
        spectacole.addAll(OrganizareSpectacole.getSpectacoleActive());
        int nr = 0;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("locrezervare.txt"))) {
            for (Spectacol spect : spectacole) {
                List<LocRezervat> rezervari = spect.getRezervat();
                for(LocRezervat rezervat : rezervari)
                {
                    bufferedWriter.write(spect.getNumeSpectacol()+","+rezervat.getL().getClass().getSimpleName()+","+rezervat.getL().getNrLoc()+","+rezervat.getL().getNrRand()+","+rezervat.getP().getNume()+","+rezervat.getP().getPrenume()+","+rezervat.getPret());
                    bufferedWriter.newLine();
                    nr ++;
                }
            }
        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully wrote " + nr + " sits!");
    }
}


