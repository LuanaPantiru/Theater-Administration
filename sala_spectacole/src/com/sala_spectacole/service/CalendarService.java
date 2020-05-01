package com.sala_spectacole.service;

import com.sala_spectacole.domain.Calendar;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class CalendarService {
    private static CalendarService instance = null;

    public static CalendarService getInstance() {
        if (instance == null)
            instance = new CalendarService();
        return instance;
    }

    public void readCalendarFromFile(OrganizareSpectacole organizare) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("calendar.txt"))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] dataFields = currentLine.split(",");
                String data = dataFields[0];
                String[] zi_luna_an = data.split("\\.");
                Calendar c = new Calendar(Integer.parseInt(zi_luna_an[0]), Integer.parseInt(zi_luna_an[1]), Integer.parseInt(zi_luna_an[2]), dataFields[1]);
                organizare.getCalendar().add(c);
            }
        } catch (IOException e) {
            System.out.println("Could not read data from file: " + e.getMessage());
            return;
        }
        Collections.sort(organizare.getCalendar());
        System.out.println("Successfully read calendar.txt");
    }

    public void writeCalendarToFile(OrganizareSpectacole organizare) {
        List<Calendar> calendar = organizare.getCalendar();
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
