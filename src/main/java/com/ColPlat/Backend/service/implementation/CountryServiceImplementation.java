package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.entity.Country;
import com.ColPlat.Backend.repository.CountryRepository;
import com.ColPlat.Backend.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryServiceImplementation implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public Country getCounytryById(Short id) {
        Optional<Country> countryOptional = countryRepository.findById(id);
        return countryOptional.orElse(null);
    }
}
