package com.sala_spectacole.service;

import com.sala_spectacole.domain.Calendar;
import com.sala_spectacole.domain.LocRezervat;
import com.sala_spectacole.persistence.CalendarRepository;
import com.sala_spectacole.persistence.LocRezervatRepository;

import java.io.*;
import java.util.List;

public class CalendarService {
    private static CalendarService instance = null;
    private final CalendarRepository calendarRepository = CalendarRepository.getInstance();
    private final LocRezervatRepository locRezervatRepository = LocRezervatRepository.getInstance();

    public static CalendarService getInstance() {
        if (instance == null)
            instance = new CalendarService();
        return instance;
    }

    public void delete(String numeSpectacol) {
        Calendar date = calendarRepository.get(numeSpectacol);
        if (date != null) {
            calendarRepository.delete(date);
        }
    }

    public double profitAn(Integer an) {
        double profit = 0;
        for (int i = 0; i < calendarRepository.getSize(); i++) {
            Calendar date = calendarRepository.get(i);
            if (date.getAn() == an) {
                List<LocRezervat> locuri = locRezervatRepository.locuriRezervateSpectacol(date.getNumeSpectacol());
                for (LocRezervat loc : locuri) {
                    profit += loc.getPret();
                }
            }
        }
        return profit;
    }

    public void addDate(String numeSpectacol, int zi, int luna, int an) {
        Calendar date = new Calendar(zi, luna, an, numeSpectacol);
        calendarRepository.add(date);
    }

    public void readCalendarFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("calendar.txt"))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                String[] dataFields = currentLine.split(",");
                String data = dataFields[0];
                String[] zi_luna_an = data.split("\\.");
                Calendar date = new Calendar(Integer.parseInt(zi_luna_an[0]), Integer.parseInt(zi_luna_an[1]), Integer.parseInt(zi_luna_an[2]), dataFields[1]);
                calendarRepository.add(date);
            }
        } catch (IOException e) {
            System.out.println("Could not read data from file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully read calendar.txt");
    }

    public void writeCalendarToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("calendar.txt"))) {
            for (int i = 0; i < calendarRepository.getSize(); i++) {
                Calendar data = calendarRepository.get(i);
                bufferedWriter.write(data.getZi() + "." + data.getLuna() + "." + data.getAn() + "," + data.getNumeSpectacol());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
            return;
        }
        System.out.println("Successfully wrote " + calendarRepository.getSize() + " dates!");
    }
}
