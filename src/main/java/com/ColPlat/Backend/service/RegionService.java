package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.entity.Region;

import java.util.List;

public interface RegionService {
    Region getRegionById(int id);
    List<Region> getRegionsByCountry(short id);
}
