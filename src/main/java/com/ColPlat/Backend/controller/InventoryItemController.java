package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.response.StorageStateResponse;
import com.ColPlat.Backend.service.InventoryItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryItemController {

    private final InventoryItemService inventoryItemService;

    @GetMapping
    public ResponseEntity<List<StorageStateResponse>> getAll() {
        return ResponseEntity.ok(inventoryItemService.getAllInventory());
    }

    @GetMapping("/location/{locationId}")
    public ResponseEntity<List<StorageStateResponse>> getByLocation(@PathVariable Long locationId) {
        return ResponseEntity.ok(inventoryItemService.getInventoryByLocation(locationId));
    }

    @PostMapping("/adjust")
    public ResponseEntity<Void> adjustInventory(@RequestBody StorageStateResponse request) {
        if (request.getAmount() > 0) {
            inventoryItemService.addInventory(request.getStorageLocationId(), request.getProductId(), request.getAmount());
        } else {
            inventoryItemService.subtractInventory(request.getStorageLocationId(), request.getProductId(), Math.abs(request.getAmount()));
        }
        return ResponseEntity.ok().build();
    }
}