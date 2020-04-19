package com.sala_spectacole.domain;

import java.util.ArrayList;
import java.util.List;

public class Spectacol implements Comparable<Spectacol>{
    private String numeSpectacol;
    private Sala s;
    private double pretNominal;
    private Integer zi;
    private Integer luna;
    private Integer an;
    private List<LocRezervat> rezervat = new ArrayList<>();

    public Spectacol(String numeSpectacol, Sala s, double pret, Integer zi, Integer luna, Integer an) {
        this.numeSpectacol = numeSpectacol;
        this.s = s;
        this.pretNominal = pret;
        this.zi = zi;
        this.luna = luna;
        this.an = an;
        this.s.setarePret(pret);
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

    public Sala getS() {
        return s;
    }

    public List<LocRezervat> getRezervat() {
        return rezervat;
    }

    public void addBilet(LocRezervat l)
    {
        rezervat.add(l);
    }

    @Override
    public int compareTo(Spectacol spectacol) {
        if(an.equals(spectacol.getAn()))
        {
            if(luna.equals(spectacol.getLuna()))
            {
                return zi.compareTo(spectacol.getZi());
            }
            else
                return luna.compareTo(spectacol.getLuna());
        }
        else
            return an.compareTo(spectacol.getAn());
    }
}
