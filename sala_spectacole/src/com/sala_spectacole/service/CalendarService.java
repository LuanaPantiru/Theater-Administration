package com.sala_spectacole.service;

import com.sala_spectacole.domain.Calendar;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalendarService {
    private static CalendarService instance = null;
    public static CalendarService getInstance(){
        if(instance == null)
            instance = new CalendarService();
        return instance;
    }
    public static void readCalendarFromFile(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("calendar.txt"))){
            String currentLine;
            while((currentLine = bufferedReader.readLine()) != null)
            {
                String[] dataFields = currentLine.split(",");
                String ceva = dataFields[0];
                String[] data = ceva.split("\\.");
                Calendar c = new Calendar(Integer.parseInt(data[0]),Integer.parseInt(data[1]),Integer.parseInt(data[2]),dataFields[1]);
                OrganizareSpectacole.getCalendar().add(c);
            }
        }catch(IOException e){
            System.out.println("Could not read data from file: "+e.getMessage());
            return;
        }
        Collections.sort(OrganizareSpectacole.getCalendar());
        System.out.println("Successfully read calendar.txt");
    }
    public static void writeCalendarToFile()
    {
        List<Calendar> calendar = OrganizareSpectacole.getCalendar();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("calendar.txt"))) {
            for (Calendar data : calendar) {
                bufferedWriter.write(data.getZi() + "." + data.getLuna() + "." + data.getAn() + "," + data.getNumeSpectacol());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully wrote " + calendar.size() + " dates!");
    }
}
