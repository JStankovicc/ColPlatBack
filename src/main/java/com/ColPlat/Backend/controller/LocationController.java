package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.response.CityResponse;
import com.ColPlat.Backend.model.dto.response.DistrictResponse;
import com.ColPlat.Backend.model.dto.response.RegionResponse;
import com.ColPlat.Backend.model.entity.Country;
import com.ColPlat.Backend.model.entity.Location;
import com.ColPlat.Backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
@RequiredArgsConstructor
public class LocationController {

    private final CountryService countryService;
    private final RegionService regionService;
    private final DistrictService districtService;
    private final CityService cityService;
    private final LocationService locationService;

    @GetMapping("/getAllCountries")
    public ResponseEntity<List<Country>> getAllCountries(){
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping("/getRegionsByCountry")
    public ResponseEntity<List<RegionResponse>> getRegionsByCountry(@RequestParam short country){
        return ResponseEntity.ok(regionService.getRegionResponsesByCountry(country));
    }

    @GetMapping("/getDistrictsByRegion")
    public ResponseEntity<List<DistrictResponse>> getAllDistricts(@RequestParam int regionId){
        return ResponseEntity.ok(districtService.getDistrictsByRegionId(regionId));
    }

    @GetMapping("/getCitiesByDistrictId")
    public ResponseEntity<List<CityResponse>> getCitiesByRegion(@RequestParam int districtId){
        return ResponseEntity.ok(cityService.getCitiesByDistrictId(districtId));
    }

    @GetMapping("/getLocationString")
    public ResponseEntity<String> getLocationString(@RequestParam Long locationId){
        return ResponseEntity.ok(locationService.getLocationStringFromId(locationId));
    }

    @GetMapping()
    public ResponseEntity<Location> getLocation(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long locationId){
        return ResponseEntity.ok(locationService.getLocationById(locationId));
    }

}
