package com.ColPlat.Backend.service.implementation;


import com.ColPlat.Backend.model.entity.Country;
import com.ColPlat.Backend.model.entity.Region;
import com.ColPlat.Backend.repository.RegionRepository;
import com.ColPlat.Backend.service.CountryService;
import com.ColPlat.Backend.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionServiceImplementation implements RegionService {

    private final RegionRepository regionRepository;
    private final CountryService countryService;
    @Override
    public Region getRegionById(int id) {
        Optional<Region> regionOptional = regionRepository.findById(id);
        return regionOptional.orElse(null);
    }

    @Override
    public List<Region> getRegionsByCountry(short id) {
        List<Region> regions = regionRepository.findAllByCountryId(id);
        return regions;
    }

    @Override
    public List<String> getRegionsNamesByCountry(short countryId) {
        List<Region> regions = regionRepository.findAllByCountryId(countryId);
        List<String> names = new ArrayList<>();
        for(Region r : regions){
            names.add(r.getName());
        }
        return names;
    }

    @Override
    public Integer findRegionId(String country, String region) {
        short countryId = countryService.findCountryId(country);
        if(countryId != 0){
            Country country1 = countryService.getCountryById(countryId);
            if(country1 != null){
                Optional<Region> regionOptional = regionRepository.findByCountryIdAndName(countryId, region);
                if(regionOptional.isPresent()){
                    return regionOptional.get().getId();
                }
            }
        }
        return 0;
    }

}
