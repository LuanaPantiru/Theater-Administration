package com.sala_spectacole.domain;

public class LocCategoria1{
    private Integer nrLoc;
    private String nrRand;
    protected double pret;
    public LocCategoria1(Integer nrLoc,String nrRand){
        this.nrLoc = nrLoc;
        this.nrRand = nrRand;
    }
    public Integer getNrLoc(){
        return nrLoc;
    }

    public double getPret() {
        return pret;
    }

    public String getNrRand() {
        return nrRand;
    }

    public void setNrLoc(Integer nrLoc) {
        this.nrLoc = nrLoc;
    }

    public void setNrRand(String nrRand) {
        this.nrRand = nrRand;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

}
