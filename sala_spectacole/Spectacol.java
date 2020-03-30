package com.sala_spectacole;

import java.util.ArrayList;
import java.util.List;

public class Spectacol implements Comparable<Spectacol>{
    private String nume_spectacol;
    private Sala s;
    private double pret_nominal;
    private Integer zi;
    private Integer luna;
    private Integer an;
    private List<LocRezervat> rezervat = new ArrayList<>();

    public Spectacol(String nume_spectacol, Sala s, double pret_nominal, Integer zi, Integer luna, Integer an) {
        this.nume_spectacol = nume_spectacol;
        this.s = s;
        this.pret_nominal = pret_nominal;
        this.zi = zi;
        this.luna = luna;
        this.an = an;
        this.s.setare_pret(pret_nominal);
    }

    public String getNume_spectacol() {
        return nume_spectacol;
    }

    public Integer getZi() {
        return zi;
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

    public void addbilet(LocRezervat l)
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
            return an.compareTo(spectacol.getZi());
    }
}
