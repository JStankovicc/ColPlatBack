package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.response.CityResponse;
import com.ColPlat.Backend.model.entity.City;

import java.util.List;

public interface CityService {
    City getCityById(int id);
    List<City> getCitiesByDistrict(int id);

    List<String> getCitiesNamesByDistrict(Integer regionId);

    List<CityResponse> getCitiesByDistrictId(int districtId);

    City getCityByName(String name);
}
