package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.response.CityResponse;
import com.ColPlat.Backend.model.entity.City;
import com.ColPlat.Backend.repository.CityRepository;
import com.ColPlat.Backend.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<City> getCitiesByDistrict(int id) {
        List<City> citiesByDistrict = cityRepository.findAllByDistrictId(id);
        return citiesByDistrict;
    }

    @Override
    public List<String> getCitiesNamesByDistrict(Integer districtId) {
        List<City> cities = cityRepository.findAllByDistrictId(districtId);
        List<String> names = new ArrayList<>();
        for(City c : cities){
            names.add(c.getName());
        }
        return names;
    }

    @Override
    public List<CityResponse> getCitiesByDistrictId(int districtId) {
        List<City> cities = cityRepository.findAllByDistrictId(districtId);
        List<CityResponse> citiesResponses = new ArrayList<>();
        for(City c : cities){
            citiesResponses.add(c.getCityResponse());
        }
        return citiesResponses;
    }

    @Override
    public City getCityByName(String name) {
        Optional<City> city = cityRepository.getByName(name);
        return city.orElse(null);
    }
}
