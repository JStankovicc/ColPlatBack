package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.response.DistrictResponse;
import com.ColPlat.Backend.model.entity.District;

import java.util.List;

public interface DistrictService {
    public District getDistrictById(int id);

    public List<DistrictResponse> getDistrictsByRegionId(int regionId);
}
