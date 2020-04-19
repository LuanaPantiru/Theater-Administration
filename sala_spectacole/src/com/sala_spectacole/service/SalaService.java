package com.sala_spectacole.service;


import com.sala_spectacole.domain.*;

import java.io.*;
import java.util.List;

public class SalaService {
    private static SalaService instance = null;
    public static SalaService getInstance(){
        if(instance == null)
            instance = new SalaService();
        return instance;
    }
    public static void readSalaFromFile()
    {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("sala.txt"))){
            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null)
            {
                String[] dataFields = currentLine.split(",");
                Sala s = new Sala(dataFields[0],Integer.parseInt(dataFields[1]),Integer.parseInt(dataFields[2]),Integer.parseInt(dataFields[3]),Integer.parseInt(dataFields[4]),Integer.parseInt(dataFields[5]),Integer.parseInt(dataFields[6]),Integer.parseInt(dataFields[7]),Integer.parseInt(dataFields[8]));
                OrganizareSpectacole.getSali().add(s);
            }
        }catch(IOException e){
            System.out.println("Could not read data from file: "+e.getMessage());
            return;
        }
        System.out.println("Successfully read sala.txt");
    }
    public static void writeSalaToFile()
    {
        List<Sala> sali = OrganizareSpectacole.getSali();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("files/sala.txt"))) {
            for (Sala s : sali) {
                bufferedWriter.write(s.getNume()+","+s.getLocuriCategoria1()+","+s.getRanduriCategoria1()+","+s.getLocuriCategoria2()+","+s.getRanduriCategoria2()+","+s.getLocuriLoja()+","+s.getRanduriLoja()+","+s.getLocuriBalcon()+","+s.getRanduriBalcon());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully wrote " + sali.size() + " theaters!");
    }
}
