package com.sala_spectacole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sala implements Pret{
    private String nume;
    private Map<String, List<Loc_Categoria1>> asezare = new HashMap<>();
    private Integer locuri_categoria1;
    private Integer randuri_categoria1;
    private Integer locuri_categoria2;
    private Integer randuri_categoria2;
    private Integer locuri_loja;
    private Integer randuri_loja;
    private Integer locuri_balcon;
    private Integer randuri_balcon;

    void aranjare_categorie(String categorie, Integer nr_locuri, Integer nr_randuri)
    {
        if (categorie == "Loc_Categoria1")
        {
            List<Loc_Categoria1> c1 = new ArrayList<>();
            for (Integer i = 0 ; i<nr_randuri;i++)
                for (Integer j =0; j< nr_locuri/nr_randuri;j++)
                {
                    Loc_Categoria1 l = new Loc_Categoria1(j+1,"R"+(i+1));
                    c1.add(l);
                }
            asezare.put(categorie,c1);
        }
        else if (categorie == "Loc_Categoria2")
        {
            List<Loc_Categoria1>c2 = new ArrayList<>();
            for (Integer i = 0 ; i<nr_randuri;i++)
                for (Integer j =0; j< nr_locuri/nr_randuri;j++)
                {
                    Loc_Categoria2 l = new Loc_Categoria2(j+1,"R"+(i+1));
                    c2.add(l);
                }
            asezare.put(categorie,c2);
        }
        else if (categorie == "Loc_Loja")
        {
            List<Loc_Categoria1> c3 = new ArrayList<>();
            for (Integer i = 0 ; i<nr_randuri;i++)
                for (Integer j =0; j< nr_locuri/nr_randuri;j++)
                {
                    Loc_Loja l = new Loc_Loja(j+1,"R"+(i+1));
                    c3.add(l);
                }
            asezare.put(categorie,c3);
        }
        else
        {
            List<Loc_Categoria1> c4 = new ArrayList<>();
            for (Integer i = 0 ; i<nr_randuri;i++)
                for (Integer j =0; j< nr_locuri/nr_randuri;j++)
                {
                    Loc_Balcon l = new Loc_Balcon(j+1,"R"+(i+1));
                    c4.add(l);
                }
            asezare.put(categorie,c4);
        }
    }
    public Sala(String nume, Integer locuri_categoria1, Integer randuri_categoria1, Integer locuri_categoria2, Integer randuri_categoria2, Integer locuri_loja, Integer randuri_loja, Integer locuri_balcon, Integer randuri_balcon) {
        this.nume = nume;
        this.locuri_categoria1 = locuri_categoria1;
        this.randuri_categoria1 = randuri_categoria1;
        this.locuri_categoria2 = locuri_categoria2;
        this.randuri_categoria2 = randuri_categoria2;
        this.locuri_loja = locuri_loja;
        this.randuri_loja = randuri_loja;
        this.locuri_balcon = locuri_balcon;
        this.randuri_balcon = randuri_balcon;
        aranjare_categorie("Loc_Categoria1",locuri_categoria1,randuri_categoria1);
        aranjare_categorie("Loc_Categoria2",locuri_categoria2,randuri_categoria2);
        aranjare_categorie("Loc_Loja",locuri_loja,randuri_loja);
        aranjare_categorie("Loc_Balcon",locuri_balcon,randuri_balcon);
    }

    public Map<String, List<Loc_Categoria1>> getAsezare() {
        return asezare;
    }

    public Integer getLocuri_categoria1() {
        return locuri_categoria1;
    }

    public Integer getRanduri_categoria1() {
        return randuri_categoria1;
    }

    public Integer getLocuri_categoria2() {
        return locuri_categoria2;
    }

    public Integer getRanduri_categoria2() {
        return randuri_categoria2;
    }

    public Integer getLocuri_loja() {
        return locuri_loja;
    }

    public Integer getRanduri_loja() {
        return randuri_loja;
    }

    public Integer getLocuri_balcon() {
        return locuri_balcon;
    }

    public Integer getRanduri_balcon() {
        return randuri_balcon;
    }
    public List<Loc_Categoria1> categorie(String categorie)
    {
        return asezare.get(categorie);
    }

    public String getNume() {
        return nume;
    }

    @Override
    public void setare_pret(double pret) {
        for(String categorie : asezare.keySet())
        {
            List<Loc_Categoria1> c1 = asezare.get(categorie);
            for (Loc_Categoria1 l : c1)
                l.setPret(pret);
        }
    }
}
