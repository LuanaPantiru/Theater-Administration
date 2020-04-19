package com.sala_spectacole.domain;

public class LocRezervat {
    private LocCategoria1 l;
    private Persoana p;
    private double pret;

    public LocRezervat(LocCategoria1 l, Persoana p, double pret) {
        this.l = l;
        this.p = p;
        this.pret = pret;
    }

    public LocCategoria1 getL() {
        return l;
    }

    public double getPret() {
        return pret;
    }

    public Persoana getP() {
        return p;
    }
}
