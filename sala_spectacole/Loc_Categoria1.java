package com.sala_spectacole;

public class Loc_Categoria1{
    private Integer nr_loc;
    private String nr_rand;
    protected double pret;
    public Loc_Categoria1(Integer nr_loc,String nr_rand){
        this.nr_loc = nr_loc;
        this.nr_rand = nr_rand;
    }
    public Integer getNr_loc(){
        return nr_loc;
    }

    public double getPret() {
        return pret;
    }

    public String getNr_rand() {
        return nr_rand;
    }

    public void setNr_loc(Integer nr_loc) {
        this.nr_loc = nr_loc;
    }

    public void setNr_rand(String nr_rand) {
        this.nr_rand = nr_rand;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

}
