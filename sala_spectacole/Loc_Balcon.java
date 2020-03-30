package com.sala_spectacole;

public class Loc_Balcon extends Loc_Categoria1{
    public Loc_Balcon(Integer nr_loc, String nr_rand) {
        super(nr_loc, nr_rand);
    }

    @Override
    public void setPret(double pret) {
        super.setPret(pret-0.4*pret);
    }
}
