package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.StorageLocationRequest;
import com.ColPlat.Backend.model.dto.response.StorageLocationResponse;
import com.ColPlat.Backend.model.entity.*;
import com.ColPlat.Backend.model.enums.ProductType;
import com.ColPlat.Backend.repository.StorageLocationRepository;
import com.ColPlat.Backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StorageLocationServiceImplementation implements StorageLocationService {

    private final StorageLocationRepository storageLocationRepository;
    private final WarehouseShelfService warehouseShelfService;
    private final ProductService productService;


    @Override
    public List<StorageLocationResponse> getAllForWarehouseShelf(Long warehouseShelfId) {
        WarehouseShelf warehouseShelf = warehouseShelfService.findById(warehouseShelfId);

        List<StorageLocation> storageLocations = storageLocationRepository.findAllByShelf(warehouseShelf);
        List<StorageLocationResponse> storageLocationResponses = new ArrayList<>();

        for(StorageLocation storageLocation : storageLocations){
            storageLocationResponses.add(getStorageLocationResponse(storageLocation));
        }

        return storageLocationResponses;
    }

    @Override
    @Transactional
    public void save(StorageLocationRequest storageLocationRequest) {
        StorageLocation storageLocation;
        WarehouseShelf warehouseShelf = warehouseShelfService.findById(storageLocationRequest.getShelfId());

        if(storageLocationRequest.getId() != null){
            Optional<StorageLocation> storageLocationOptional = storageLocationRepository.findById(storageLocationRequest.getId());
            storageLocation = storageLocationOptional.get();
        }else storageLocation = new StorageLocation();

        String barcode;
        if(storageLocationRequest.isGenerateBarcode()){
            barcode = generateBarcode(warehouseShelf, storageLocationRequest.getName());
        }else barcode = storageLocationRequest.getBarcode();

        if(storageLocationRequest.getPreferredProductId() != null){
            Product product = productService.findById(storageLocationRequest.getPreferredProductId());
            storageLocation.setPreferredProduct(product);
        }

        if(storageLocationRequest.getPreferredType() != null){
            storageLocation.setPreferredType(storageLocationRequest.getPreferredType());
        }

        storageLocation.setName(storageLocationRequest.getName());
        storageLocation.setShelf(warehouseShelf);
        storageLocation.setMaxWeight(storageLocationRequest.getMaxWeight());
        storageLocation.setCurrentWeight(storageLocationRequest.getCurrentWeight());
        storageLocation.setMaxVolume(storageLocationRequest.getMaxVolume());
        storageLocation.setCurrentVolume(storageLocationRequest.getCurrentVolume());
        storageLocation.setBarcode(barcode);
        storageLocation.setOccupied(storageLocationRequest.isOccupied());

        storageLocationRepository.save(storageLocation);
    }

    @Override
    public StorageLocation findById(Long storageLocationId) {
        return storageLocationRepository.findById(storageLocationId).orElse(null);
    }


    public StorageLocationResponse getStorageLocationResponse(StorageLocation storageLocation){
        return StorageLocationResponse.builder()
                .id(storageLocation.getId())
                .name(storageLocation.getName())
                .shelfId(storageLocation.getShelf().getId())
                .barcode(storageLocation.getBarcode())
                .maxWeight(storageLocation.getMaxWeight())
                .currentWeight(storageLocation.getCurrentWeight())
                .maxVolume(storageLocation.getMaxVolume())
                .currentVolume(storageLocation.getCurrentVolume())
                .preferredProductId(storageLocation.getPreferredProduct())
                .preferredType(storageLocation.getPreferredType())
                .isOccupied(storageLocation.isOccupied())
                .build();
    }

    @Transactional(readOnly = true)
    public String generateBarcode(WarehouseShelf shelf, String name){
        WarehouseAisle warehouseAisle = shelf.getAisle();
        WarehouseZone warehouseZone = warehouseAisle.getZone();
        Warehouse warehouse = warehouseZone.getWarehouse();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(warehouse.getCode());
        stringBuilder.append("-");
        stringBuilder.append(warehouseZone.getCode());
        stringBuilder.append("-");
        stringBuilder.append(warehouseAisle.getCode());
        stringBuilder.append("-");
        stringBuilder.append(shelf.getLevel());
        stringBuilder.append("-");
        stringBuilder.append(name);

        return stringBuilder.toString();

    }
}
