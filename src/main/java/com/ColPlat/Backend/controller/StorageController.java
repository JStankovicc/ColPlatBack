package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.StorageLocationRequest;
import com.ColPlat.Backend.model.dto.request.WarehouseAisleRequest;
import com.ColPlat.Backend.model.dto.request.WarehouseShelfRequest;
import com.ColPlat.Backend.model.dto.response.StorageLocationResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseAisleResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseShelfResponse;
import com.ColPlat.Backend.model.entity.WarehouseAisle;
import com.ColPlat.Backend.service.StorageLocationService;
import com.ColPlat.Backend.service.WarehouseAisleService;
import com.ColPlat.Backend.service.WarehouseShelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
public class StorageController {

    private final WarehouseAisleService warehouseAisleService;
    private final WarehouseShelfService warehouseShelfService;
    private final StorageLocationService storageLocationService;

    @GetMapping("/aisle")
    public ResponseEntity<List<WarehouseAisleResponse>> getAllAislesForZone(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseZoneId){
        return ResponseEntity.ok(warehouseAisleService.getAllForWarehouseZone(warehouseZoneId));
    }

    @PostMapping("/aisle")
    public void postWarehouseAisle(@RequestHeader("Authorization") String authorizationHeader, @RequestBody WarehouseAisleRequest warehouseAisleRequest){
        warehouseAisleService.save(warehouseAisleRequest);
    }

    @GetMapping("/shelf")
    public ResponseEntity<List<WarehouseShelfResponse>> getAllShelfsForAisle(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseAisleId){
        return ResponseEntity.ok(warehouseShelfService.getAllForWarehouseAisle(warehouseAisleId));
    }

    @PostMapping("/shelf")
    public void postWarehouseShelf(@RequestHeader("Authorization") String authorizationHeader, @RequestBody WarehouseShelfRequest warehouseShelfRequest){
        warehouseShelfService.save(warehouseShelfRequest);
    }

    @GetMapping("/storageLocation")
    public ResponseEntity<List<StorageLocationResponse>> getAllStorageLocations(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseShelfId){
        return ResponseEntity.ok(storageLocationService.getAllForWarehouseShelf(warehouseShelfId));
    }

    @PostMapping("/storageLocation")
    public void postStorageLocation(@RequestHeader("Authorization") String authorizationHeader, @RequestBody StorageLocationRequest storageLocationRequest){
        storageLocationService.save(storageLocationRequest);
    }
}
