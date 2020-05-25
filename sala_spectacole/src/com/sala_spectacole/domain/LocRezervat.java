package com.sala_spectacole.domain;

public class LocRezervat {
    private LocCategoria1 loc;
    private String cnpPersoana;
    private String numeSpectacol;
    private double pret;

    public LocRezervat(){

    }
    public LocRezervat(LocCategoria1 loc, String cnpPersoana, String numeSpectacol,double pret) {
        this.loc = loc;
        this.cnpPersoana = cnpPersoana;
        this.pret = pret;
        this.numeSpectacol=numeSpectacol;
    }

    public LocCategoria1 getLoc() {
        return loc;
    }

    public double getPret() {
        return pret;
    }

    public String getCnpPersoana() {
        return cnpPersoana;
    }

    public String getNumeSpectacol() {
        return numeSpectacol;
    }

    public void setLoc(LocCategoria1 loc) {
        this.loc = loc;
    }

    public void setCnpPersoana(String cnpPersoana) {
        this.cnpPersoana = cnpPersoana;
    }

    public void setNumeSpectacol(String numeSpectacol) {
        this.numeSpectacol = numeSpectacol;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }
}
