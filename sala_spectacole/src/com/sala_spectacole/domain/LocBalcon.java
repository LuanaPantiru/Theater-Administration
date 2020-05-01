package com.sala_spectacole.domain;

public class LocBalcon extends LocCategoria1 {
    public LocBalcon(Integer nrLoc, String nrRand) {
        super(nrLoc, nrRand);
    }

    @Override
    public void setPret(double pret) {
        super.setPret(pret - 0.4 * pret);
    }
}
