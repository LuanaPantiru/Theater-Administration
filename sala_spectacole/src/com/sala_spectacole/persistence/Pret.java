package com.sala_spectacole.persistence;

import com.sala_spectacole.domain.LocCategoria1;

import java.util.List;
import java.util.Map;

public interface Pret {
    void setarePret(double pret, Map<String, List<LocCategoria1>> asezare);
}
