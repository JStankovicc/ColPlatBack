package com.ColPlat.Backend.service.implementation;


import com.ColPlat.Backend.model.entity.Region;
import com.ColPlat.Backend.repository.RegionRepository;
import com.ColPlat.Backend.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionServiceImplementation implements RegionService {

    private final RegionRepository regionRepository;

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
}
