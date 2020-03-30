package com.sala_spectacole;

public class Loc_Categoria2 extends Loc_Categoria1 {
    public Loc_Categoria2(Integer nr_loc, String nr_rand) {
        super(nr_loc, nr_rand);
    }

    @Override
    public void setPret(double pret) {
        super.setPret(pret-0.2*pret);
    }
}
