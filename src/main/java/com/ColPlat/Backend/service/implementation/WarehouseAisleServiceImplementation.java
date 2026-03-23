package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.WarehouseAisleRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseAisleResponse;
import com.ColPlat.Backend.model.entity.WarehouseAisle;
import com.ColPlat.Backend.model.entity.WarehouseZone;
import com.ColPlat.Backend.repository.WarehouseAisleRepository;
import com.ColPlat.Backend.service.WarehouseAisleService;
import com.ColPlat.Backend.service.WarehouseZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseAisleServiceImplementation implements WarehouseAisleService {

    private final WarehouseAisleRepository warehouseAisleRepository;

    private final WarehouseZoneService warehouseZoneService;

    @Override
    public List<WarehouseAisleResponse> getAllForWarehouseZone(Long warehouseZoneId) {
        WarehouseZone warehouseZone = warehouseZoneService.findById(warehouseZoneId);

        List<WarehouseAisle> warehouseAisles = warehouseAisleRepository.findAllByZone(warehouseZone);

        List<WarehouseAisleResponse> warehouseAisleResponses = new ArrayList<>();
        for (WarehouseAisle warehouseAisle : warehouseAisles){
            warehouseAisleResponses.add(getWarehouseAisleResponse(warehouseAisle));
        }

        return warehouseAisleResponses;
    }

    @Override
    public void save(WarehouseAisleRequest warehouseAisleRequest) {
        WarehouseAisle warehouseAisle;
        if(warehouseAisleRequest.getId() != null){
            Optional<WarehouseAisle> warehouseAisleOptional = warehouseAisleRepository.findById(warehouseAisleRequest.getId());
            warehouseAisle = warehouseAisleOptional.get();
        }else warehouseAisle = new WarehouseAisle();

        warehouseAisle.setZone(warehouseZoneService.findById(warehouseAisleRequest.getWarehouseZoneId()));
        warehouseAisle.setCode(warehouseAisleRequest.getCode());
        warehouseAisle.setName(warehouseAisleRequest.getName());

        warehouseAisleRepository.save(warehouseAisle);
    }

    @Override
    public WarehouseAisle findById(Long warehouseAisleId) {
        return warehouseAisleRepository.findById(warehouseAisleId).orElse(null);
    }

    public WarehouseAisleResponse getWarehouseAisleResponse(WarehouseAisle warehouseAisle){
        return WarehouseAisleResponse.builder()
                .id(warehouseAisle.getId())
                .warehouseZoneId(warehouseAisle.getZone().getId())
                .code(warehouseAisle.getCode())
                .name(warehouseAisle.getName())
                .build();
    }
}
