package com.sala_spectacole.service;

import com.sala_spectacole.domain.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpectacolService {
    private static SpectacolService instance = null;
    public static SpectacolService getInstance(){
        if (instance == null)
            instance = new SpectacolService();
        return instance;
    }
    public static void readSpectacolFromFile()
    {
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("spectacol.txt"))){
            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null)
            {
                String[] dataFields = currentLine.split(",");
                Sala s = OrganizareSpectacole.findSala(dataFields[1]);
                Sala sala = new Sala(s.getNume(),s.getLocuriCategoria1(),s.getRanduriCategoria1(),s.getLocuriCategoria2(),s.getRanduriCategoria2(),s.getLocuriLoja(),s.getRanduriLoja(),s.getLocuriBalcon(),s.getRanduriBalcon());
                sala.setarePret(Double.parseDouble(dataFields[2]));
                String[] data= dataFields[3].split("\\.");
                Spectacol spect = new Spectacol(dataFields[0],sala,Double.parseDouble(dataFields[2]),Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]));
                OrganizareSpectacole.getSpectacoleActive().add(spect);

            }
        }catch(IOException e){
            System.out.println("Could not read data from file: "+e.getMessage());
            return;
        }
        Collections.sort(OrganizareSpectacole.getSpectacoleActive());
        System.out.println("Successfully read spectacol.txt");
    }
    public static void writeSpectacolToFile()
    {
        List<Spectacol> spectacole = new ArrayList<>(OrganizareSpectacole.getSpectacoleInchise());
        spectacole.addAll(OrganizareSpectacole.getSpectacoleActive());
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("spectacol.txt"))) {
            for (Spectacol spec : spectacole) {
                bufferedWriter.write(spec.getNumeSpectacol()+","+spec.getS().getNume()+","+spec.getPretNominal()+","+spec.getZi()+"."+spec.getLuna()+"."+spec.getAn());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully wrote " + spectacole.size() + " shows!");
    }
}
