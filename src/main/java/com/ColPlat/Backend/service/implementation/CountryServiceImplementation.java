package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.entity.Country;
import com.ColPlat.Backend.repository.CountryRepository;
import com.ColPlat.Backend.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryServiceImplementation implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public Country getCountryById(Short id) {
        Optional<Country> countryOptional = countryRepository.findById(id);
        return countryOptional.orElse(null);
    }

    @Override
    public List<String> getAllCountriesNames() {
        List<Country> countries = countryRepository.findAll();
        List<String> names = new ArrayList<>();
        for(Country c : countries){
            names.add(c.getName());
        }
        return names;
    }

    @Override
    public short findCountryId(String country) {
        Optional<Country> countryOptional = countryRepository.findByName(country);
        if(countryOptional.isPresent()){
            Country c = countryOptional.get();
            return c.getId();
        }
        return 0;
    }
}
