package com.sala_spectacole.domain;

public class LocRezervat {
    private LocCategoria1 loc;
    private Persoana persoana;
    private double pret;

    public LocRezervat(LocCategoria1 loc, Persoana persoana, double pret) {
        this.loc = loc;
        this.persoana = persoana;
        this.pret = pret;
    }

    public LocCategoria1 getLoc() {
        return loc;
    }

    public double getPret() {
        return pret;
    }

    public Persoana getPersoana() {
        return persoana;
    }
}
