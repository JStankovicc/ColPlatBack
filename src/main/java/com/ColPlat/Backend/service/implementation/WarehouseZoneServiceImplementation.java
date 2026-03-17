package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.CreateWarehouseZoneRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseZoneResponse;
import com.ColPlat.Backend.model.entity.Warehouse;
import com.ColPlat.Backend.model.entity.WarehouseZone;
import com.ColPlat.Backend.repository.WarehouseZoneRepository;
import com.ColPlat.Backend.service.WarehouseService;
import com.ColPlat.Backend.service.WarehouseZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseZoneServiceImplementation implements WarehouseZoneService {

    private final WarehouseZoneRepository warehouseZoneRepository;
    private final WarehouseService warehouseService;


    @Override
    public void createWarehouseZone(CreateWarehouseZoneRequest request) {
        Warehouse warehouse = warehouseService.findById(request.getWarehouseId());

        WarehouseZone warehouseZone = WarehouseZone.builder()
                .name(request.getName())
                .code(request.getCode())
                .type(request.getType())
                .warehouse(warehouse)
                .build();

        warehouseZoneRepository.save(warehouseZone);
    }

    @Override
    public List<WarehouseZoneResponse> getAllWarehouseZonesByWarehouse(Warehouse warehouse) {
        List<WarehouseZone> warehouseZones = warehouseZoneRepository.findAllByWarehouse(warehouse);
        List<WarehouseZoneResponse> warehouseZoneResponses = new ArrayList<>();
        for(WarehouseZone warehouseZone : warehouseZones){
            warehouseZoneResponses.add(WarehouseZoneResponse.builder()
                    .id(warehouseZone.getId())
                    .name(warehouseZone.getName())
                    .code(warehouseZone.getCode())
                    .warehouseId(warehouseZone.getWarehouse().getId())
                    .type(warehouseZone.getType())
                    .build());
        }
        return warehouseZoneResponses;
    }

    @Override
    @Transactional
    public void updateWarehouseZone(Long warehouseZoneId, CreateWarehouseZoneRequest request) {
        Optional<WarehouseZone> warehouseOptional = warehouseZoneRepository.findById(warehouseZoneId);
        if (warehouseOptional.isPresent()){
            WarehouseZone warehouseZone = warehouseOptional.get();

            warehouseZone.setName(request.getName());
            warehouseZone.setCode(request.getCode());
            warehouseZone.setType(request.getType());
            warehouseZone.setWarehouse(warehouseService.findById(request.getWarehouseId()));

            warehouseZoneRepository.save(warehouseZone);
        }
    }

    @Override
    public void deleteWarehouseZone(Long warehouseZoneId) {
        warehouseZoneRepository.deleteById(warehouseZoneId);
    }
}
