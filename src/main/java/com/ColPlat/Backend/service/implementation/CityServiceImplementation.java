package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.entity.City;
import com.ColPlat.Backend.repository.CityRepository;
import com.ColPlat.Backend.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityServiceImplementation implements CityService {

    private final CityRepository cityRepository;

    @Override
    public City getCityById(int id) {
        Optional<City> cityOptional = cityRepository.findById(id);
        return cityOptional.orElse(null);
    }

    @Override
    public List<City> getCitiesByRegion(int id) {
        List<City> citiesByRegion = cityRepository.findAllByRegionId(id);
        return citiesByRegion;
    }
}
