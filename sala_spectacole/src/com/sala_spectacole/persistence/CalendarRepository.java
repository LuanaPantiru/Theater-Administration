package com.sala_spectacole.persistence;

import com.sala_spectacole.domain.Calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalendarRepository {
    private static CalendarRepository instance;
    private static final List<Calendar> dates = new ArrayList<>();

    private CalendarRepository() {
    }

    public static CalendarRepository getInstance() {
        if (instance == null) {
            instance = new CalendarRepository();
        }
        return instance;
    }

    public void add(Calendar date) {
        dates.add(date);
        Collections.sort(dates);
    }

    public Calendar get(int id) {
        return dates.get(id);
    }

    public Calendar get(String numeSpectacol) {
        for (Calendar date : dates) {
            if (date.getNumeSpectacol().equals(numeSpectacol))
                return date;
        }
        return null;
    }

    public int getSize() {
        return dates.size();
    }

    public void delete(Calendar date) {
        dates.remove(date);
    }

    public List<Calendar> spectacoleActive(Integer zi, Integer luna, Integer an) {
        List<Calendar> specActive = new ArrayList<>(dates);
        for (Calendar date : dates) {
            if (date.beforeDate(zi, luna, an)) {
                specActive.remove(date);
            }
        }
        return specActive;
    }


}
