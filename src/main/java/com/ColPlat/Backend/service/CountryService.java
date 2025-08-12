package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.entity.Country;

import java.util.List;

public interface CountryService {
    Country getCountryById(Short id);

    List<String> getAllCountriesNames();

    short findCountryId(String country);
}
