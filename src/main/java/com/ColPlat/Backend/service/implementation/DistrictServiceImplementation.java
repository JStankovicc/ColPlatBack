package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.response.DistrictResponse;
import com.ColPlat.Backend.model.entity.District;
import com.ColPlat.Backend.repository.DistrictRepository;
import com.ColPlat.Backend.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistrictServiceImplementation implements DistrictService {

    private final DistrictRepository districtRepository;


    @Override
    public District getDistrictById(int id) {
        Optional<District> district = districtRepository.findById(id);
        return district.orElse(null);
    }

    @Override
    public List<DistrictResponse> getDistrictsByRegionId(int regionId) {
        List<District> districts = districtRepository.findAllByRegionId(regionId);
        List<DistrictResponse> districtResponses = new ArrayList<>();
        for (District district : districts) {
            districtResponses.add(district.getDistrictResponse());
        }
        return districtResponses;
    }
}
