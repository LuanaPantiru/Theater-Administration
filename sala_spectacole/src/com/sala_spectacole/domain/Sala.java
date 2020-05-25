package com.sala_spectacole.domain;

public class Sala {
    private String nume;
    private Integer locuriCategoria1;
    private Integer randuriCategoria1;
    private Integer locuriCategoria2;
    private Integer randuriCategoria2;
    private Integer locuriLoja;
    private Integer randuriLoja;
    private Integer locuriBalcon;
    private Integer randuriBalcon;


    public Sala() {
    }

    public Sala(String nume, Integer locuriCategoria1, Integer randuriCategoria1, Integer locuriCategoria2, Integer randuriCategoria2, Integer locuriLoja, Integer randuriLoja, Integer locuriBalcon, Integer randuriBalcon) {
        this.nume = nume;
        this.locuriCategoria1 = locuriCategoria1;
        this.randuriCategoria1 = randuriCategoria1;
        this.locuriCategoria2 = locuriCategoria2;
        this.randuriCategoria2 = randuriCategoria2;
        this.locuriLoja = locuriLoja;
        this.randuriLoja = randuriLoja;
        this.locuriBalcon = locuriBalcon;
        this.randuriBalcon = randuriBalcon;
    }


    public Integer getLocuriCategoria1() {
        return locuriCategoria1;
    }

    public Integer getRanduriCategoria1() {
        return randuriCategoria1;
    }

    public Integer getLocuriCategoria2() {
        return locuriCategoria2;
    }

    public Integer getRanduriCategoria2() {
        return randuriCategoria2;
    }

    public Integer getLocuriLoja() {
        return locuriLoja;
    }

    public Integer getRanduriLoja() {
        return randuriLoja;
    }

    public Integer getLocuriBalcon() {
        return locuriBalcon;
    }

    public Integer getRanduriBalcon() {
        return randuriBalcon;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setLocuriCategoria1(Integer locuriCategoria1) {
        this.locuriCategoria1 = locuriCategoria1;
    }

    public void setRanduriCategoria1(Integer randuriCategoria1) {
        this.randuriCategoria1 = randuriCategoria1;
    }

    public void setLocuriCategoria2(Integer locuriCategoria2) {
        this.locuriCategoria2 = locuriCategoria2;
    }

    public void setRanduriCategoria2(Integer randuriCategoria2) {
        this.randuriCategoria2 = randuriCategoria2;
    }

    public void setLocuriLoja(Integer locuriLoja) {
        this.locuriLoja = locuriLoja;
    }

    public void setRanduriLoja(Integer randuriLoja) {
        this.randuriLoja = randuriLoja;
    }

    public void setLocuriBalcon(Integer locuriBalcon) {
        this.locuriBalcon = locuriBalcon;
    }

    public void setRanduriBalcon(Integer randuriBalcon) {
        this.randuriBalcon = randuriBalcon;
    }
}
