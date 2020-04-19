package com.sala_spectacole.domain;

import com.sala_spectacole.Pret;
import com.sala_spectacole.domain.LocBalcon;
import com.sala_spectacole.domain.LocCategoria1;
import com.sala_spectacole.domain.LocCategoria2;
import com.sala_spectacole.domain.LocLoja;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sala implements Pret {
    private String nume;
    private Map<String, List<LocCategoria1>> asezare = new HashMap<>();
    private Integer locuriCategoria1;
    private Integer randuriCategoria1;
    private Integer locuriCategoria2;
    private Integer randuriCategoria2;
    private Integer locuriLoja;
    private Integer randuriLoja;
    private Integer locuriBalcon;
    private Integer randuriBalcon;

    void aranjareCategorie(String categorie, Integer nrLocuri, Integer nrRanduri)
    {
        if (categorie == "LocCategoria1")
        {
            List<LocCategoria1> c1 = new ArrayList<>();
            for (Integer i = 0 ; i<nrRanduri;i++)
                for (Integer j =0; j< nrLocuri/nrRanduri;j++)
                {
                    LocCategoria1 l = new LocCategoria1(j+1,"R"+(i+1));
                    c1.add(l);
                }
            asezare.put(categorie,c1);
        }
        else if (categorie == "LocCategoria2")
        {
            List<LocCategoria1>c2 = new ArrayList<>();
            for (Integer i = 0 ; i<nrRanduri;i++)
                for (Integer j =0; j< nrLocuri/nrRanduri;j++)
                {
                    LocCategoria2 l = new LocCategoria2(j+1,"R"+(i+1));
                    c2.add(l);
                }
            asezare.put(categorie,c2);
        }
        else if (categorie == "LocLoja")
        {
            List<LocCategoria1> c3 = new ArrayList<>();
            for (Integer i = 0 ; i<nrRanduri;i++)
                for (Integer j =0; j< nrLocuri/nrRanduri;j++)
                {
                    LocLoja l = new LocLoja(j+1,"R"+(i+1));
                    c3.add(l);
                }
            asezare.put(categorie,c3);
        }
        else
        {
            List<LocCategoria1> c4 = new ArrayList<>();
            for (Integer i = 0 ; i<nrRanduri;i++)
                for (Integer j =0; j< nrLocuri/nrRanduri;j++)
                {
                    LocBalcon l = new LocBalcon(j+1,"R"+(i+1));
                    c4.add(l);
                }
            asezare.put(categorie,c4);
        }
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
        aranjareCategorie("LocCategoria1",locuriCategoria1,randuriCategoria1);
        aranjareCategorie("LocCategoria2",locuriCategoria2,randuriCategoria2);
        aranjareCategorie("LocLoja",locuriLoja,randuriLoja);
        aranjareCategorie("LocBalcon",locuriBalcon,randuriBalcon);
    }

    public Map<String, List<LocCategoria1>> getAsezare() {
        return asezare;
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
    public List<LocCategoria1> categorie(String categorie)
    {
        return asezare.get(categorie);
    }

    public String getNume() {
        return nume;
    }

    @Override
    public void setarePret(double pret) {
        for(String categorie : asezare.keySet())
        {
            List<LocCategoria1> c1 = asezare.get(categorie);
            for (LocCategoria1 l : c1)
                l.setPret(pret);
        }
    }
}
