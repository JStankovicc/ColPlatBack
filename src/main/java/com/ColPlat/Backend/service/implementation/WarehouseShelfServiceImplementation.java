package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.WarehouseShelfRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseShelfResponse;
import com.ColPlat.Backend.model.entity.WarehouseAisle;
import com.ColPlat.Backend.model.entity.WarehouseShelf;
import com.ColPlat.Backend.repository.WarehouseShelfRepository;
import com.ColPlat.Backend.service.WarehouseAisleService;
import com.ColPlat.Backend.service.WarehouseShelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseShelfServiceImplementation implements WarehouseShelfService {

    private final WarehouseShelfRepository warehouseShelfRepository;
    private final WarehouseAisleService warehouseAisleService;


    @Override
    public List<WarehouseShelfResponse> getAllForWarehouseAisle(Long warehouseAisleId) {
        WarehouseAisle warehouseAisle = warehouseAisleService.findById(warehouseAisleId);

        List<WarehouseShelf> warehouseShelves = warehouseShelfRepository.findAllByAisle(warehouseAisle);
        List<WarehouseShelfResponse> warehouseShelfResponses = new ArrayList<>();

        for (WarehouseShelf warehouseShelf : warehouseShelves){
            warehouseShelfResponses.add(getWarehouseShelfResponse(warehouseShelf));
        }

        return warehouseShelfResponses;
    }

    @Override
    public void save(WarehouseShelfRequest warehouseShelfRequest) {
        WarehouseShelf warehouseShelf;
        if(warehouseShelfRequest.getId() != null){
            Optional<WarehouseShelf> warehouseShelfOptional = warehouseShelfRepository.findById(warehouseShelfRequest.getId());
            warehouseShelf = warehouseShelfOptional.get();
        }else warehouseShelf = new WarehouseShelf();

        warehouseShelf.setName(warehouseShelfRequest.getName());
        warehouseShelf.setLevel(warehouseShelfRequest.getLevel());
        warehouseShelf.setName(warehouseShelfRequest.getName());
        warehouseShelf.setAisle(warehouseAisleService.findById(warehouseShelfRequest.getWarehouseAisleId()));

        warehouseShelfRepository.save(warehouseShelf);
    }

    @Override
    public WarehouseShelf findById(Long warehouseShelfId) {
        return warehouseShelfRepository.findById(warehouseShelfId).orElse(null);
    }

    public WarehouseShelfResponse getWarehouseShelfResponse(WarehouseShelf warehouseShelf){
        return WarehouseShelfResponse.builder()
                .id(warehouseShelf.getId())
                .name(warehouseShelf.getName())
                .warehouseAisleId(warehouseShelf.getId())
                .level(warehouseShelf.getLevel())
                .build();
    }
}
