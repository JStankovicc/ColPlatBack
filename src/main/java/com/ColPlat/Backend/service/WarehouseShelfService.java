package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.WarehouseShelfRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseShelfResponse;
import com.ColPlat.Backend.model.entity.WarehouseShelf;

import java.util.List;

public interface WarehouseShelfService {


    List<WarehouseShelfResponse> getAllForWarehouseAisle(Long warehouseAisleId);

    void save(WarehouseShelfRequest warehouseShelfRequest);

    WarehouseShelf findById(Long warehouseShelfId);
}
