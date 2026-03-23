package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.WarehouseAisleRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseAisleResponse;
import com.ColPlat.Backend.model.entity.WarehouseAisle;

import java.util.List;

public interface WarehouseAisleService {
    List<WarehouseAisleResponse> getAllForWarehouseZone(Long warehouseZoneId);

    void save(WarehouseAisleRequest warehouseAisleRequest);

    WarehouseAisle findById(Long warehouseAisleId);
}
