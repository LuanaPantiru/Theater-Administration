package com.sala_spectacole.domain;

import com.sala_spectacole.domain.LocCategoria1;

public class LocLoja extends LocCategoria1 {
    public LocLoja(Integer nrLoc, String nrRand) {
        super(nrLoc, nrRand);
    }

    @Override
    public void setPret(double pret) {
        super.setPret(pret+0.3*pret);
    }
}
