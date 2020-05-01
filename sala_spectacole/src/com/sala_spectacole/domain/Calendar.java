package com.sala_spectacole.domain;

public class Calendar implements Comparable<Calendar> {
    private Integer zi;
    private Integer luna;
    private Integer an;
    private String numeSpectacol;

    public Calendar(int zi, int luna, int an, String numeSpectacol) {
        this.zi = zi;
        this.luna = luna;
        this.an = an;
        this.numeSpectacol = numeSpectacol;
    }

    public int getZi() {
        return zi;
    }

    public void setZi(Integer zi) {
        this.zi = zi;
    }

    public int getLuna() {
        return luna;
    }

    public void setLuna(Integer luna) {
        this.luna = luna;
    }

    public int getAn() {
        return an;
    }

    public void setAn(Integer an) {
        this.an = an;
    }

    public String getNumeSpectacol() {
        return numeSpectacol;
    }

    public void setNumeSpectacol(String numeSpectacol) {
        this.numeSpectacol = numeSpectacol;
    }

    public boolean beforeDate(int zi, int luna, int an) {
        if (this.an.equals(an)) {
            if (this.luna.equals(luna))
                return this.zi.compareTo(zi) <= 0;
            return this.luna.compareTo(luna) <= 0;
        }
        return this.an.compareTo(an) <= 0;
    }

    @Override
    public int compareTo(Calendar calendar) {
        if (an.equals(calendar.getAn())) {
            if (luna.equals(calendar.getLuna())) {
                return zi.compareTo(calendar.getZi());
            } else
                return luna.compareTo(calendar.getLuna());
        } else
            return an.compareTo(calendar.getAn());
    }
}
