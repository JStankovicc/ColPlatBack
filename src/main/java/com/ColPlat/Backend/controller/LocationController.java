package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.entity.Country;
import com.ColPlat.Backend.service.CityService;
import com.ColPlat.Backend.service.CountryService;
import com.ColPlat.Backend.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {

    private final CountryService countryService;
    private final RegionService regionService;
    private final CityService cityService;

    @GetMapping("/getAllCountries")
    public ResponseEntity<List<String>> getAllCountries(){
        return ResponseEntity.ok(countryService.getAllCountriesNames());
    }

    @GetMapping("/getRegionsByCountry")
    public ResponseEntity<List<String>> getRegionsByCountry(@RequestParam String country){
        return ResponseEntity.ok(regionService.getRegionsNamesByCountry(countryService.findCountryId(country)));
    }

    @GetMapping("/getCitiesByRegion")
    public ResponseEntity<List<String>> getCitiesByRegion(@RequestParam String country, @RequestParam String region){
        return ResponseEntity.ok(cityService.getCitiesNamesByRegion(regionService.findRegionId(country,region)));
    }

}
