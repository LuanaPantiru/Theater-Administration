package com.sala_spectacole.domain;

import com.sala_spectacole.domain.LocCategoria1;

public class LocCategoria2 extends LocCategoria1 {
    public LocCategoria2(Integer nrLoc, String nrRand) {
        super(nrLoc, nrRand);
    }

    @Override
    public void setPret(double pret) {
        super.setPret(pret-0.2*pret);
    }
}
