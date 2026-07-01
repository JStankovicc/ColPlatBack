package com.ColPlat.Backend;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataLoader {

    /*
     * Svi podaci se ucitavaju putem SQL skripte: newabp_with_alldb_geo.sql
     * Ova klasa je ostavljena samo kao mesto za buduce programsko punjenje podataka.
     */

    //@PostConstruct
    @Transactional
    public void executeDataLoader() {
        // DataLoader je redundantan - SQL fajl pokriva sve podatke.
    }
}
