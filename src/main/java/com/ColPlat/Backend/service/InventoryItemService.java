package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.response.StorageStateResponse;
import com.ColPlat.Backend.model.entity.WarehouseZone;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InventoryItemService {
    void addInventory(Long locationId, Long productId, Double amount);
    void subtractInventory(Long locationId, Long productId, Double amount);
    List<StorageStateResponse> getAllInventory();
    List<StorageStateResponse> getInventoryByLocation(Long locationId);
}
