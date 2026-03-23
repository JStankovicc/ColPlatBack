package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.CreateWarehouseRequest;
import com.ColPlat.Backend.model.dto.request.CreateWarehouseZoneRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseZoneResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Warehouse;
import com.ColPlat.Backend.model.entity.WarehouseZone;

import java.util.List;

public interface WarehouseZoneService {

    public void createWarehouseZone(CreateWarehouseZoneRequest request);

    public List<WarehouseZoneResponse> getAllWarehouseZonesByWarehouse(Warehouse warehouse);

    public void updateWarehouseZone(Long warehouseZoneId, CreateWarehouseZoneRequest request);

    public void deleteWarehouseZone(Long warehouseZoneId);

    WarehouseZone findById(Long id);
}
