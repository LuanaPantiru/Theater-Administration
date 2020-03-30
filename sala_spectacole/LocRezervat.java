package com.sala_spectacole;

public class LocRezervat {
    private Loc_Categoria1 l;
    private Persoana p;
    private double pret;

    public LocRezervat(Loc_Categoria1 l, Persoana p, double pret) {
        this.l = l;
        this.p = p;
        this.pret = pret;
    }

    public Loc_Categoria1 getL() {
        return l;
    }

    public Persoana getP() {
        return p;
    }
}
