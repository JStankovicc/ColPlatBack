package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.entity.City;

import java.util.List;

public interface CityService {
    City getCityById(int id);
    List<City> getCitiesByRegion(int id);

    List<String> getCitiesNamesByRegion(Integer regionId);
}
