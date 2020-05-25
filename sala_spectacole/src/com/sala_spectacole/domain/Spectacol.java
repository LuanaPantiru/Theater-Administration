package com.sala_spectacole.domain;

import java.util.ArrayList;
import java.util.List;

public class Spectacol implements Comparable<Spectacol> {
    private String numeSpectacol;
    private String numeSala;
    private double pretNominal;
    private Integer zi;
    private Integer luna;
    private Integer an;

    public Spectacol() {
    }

    public Spectacol(String numeSpectacol, String numeSala, double pret, Integer zi, Integer luna, Integer an) {
        this.numeSpectacol = numeSpectacol;
        this.numeSala = numeSala;
        this.pretNominal = pret;
        this.zi = zi;
        this.luna = luna;
        this.an = an;
    }

    public String getNumeSpectacol() {
        return numeSpectacol;
    }

    public Integer getZi() {
        return zi;
    }

    public double getPretNominal() {
        return pretNominal;
    }

    public Integer getLuna() {
        return luna;
    }

    public Integer getAn() {
        return an;
    }

    public String getSala() {
        return numeSala;
    }

    public void setNumeSpectacol(String numeSpectacol) {
        this.numeSpectacol = numeSpectacol;
    }

    public void setNumeSala(String numeSala) {
        this.numeSala = numeSala;
    }

    public void setPretNominal(double pretNominal) {
        this.pretNominal = pretNominal;
    }

    public void setZi(Integer zi) {
        this.zi = zi;
    }

    public void setLuna(Integer luna) {
        this.luna = luna;
    }

    public void setAn(Integer an) {
        this.an = an;
    }

    @Override
    public int compareTo(Spectacol spectacol) {
        if (an.equals(spectacol.getAn())) {
            if (luna.equals(spectacol.getLuna())) {
                return zi.compareTo(spectacol.getZi());
            } else
                return luna.compareTo(spectacol.getLuna());
        } else
            return an.compareTo(spectacol.getAn());
    }
}
