package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.CreateOfficeRequest;
import com.ColPlat.Backend.model.dto.request.CreateWarehouseRequest;
import com.ColPlat.Backend.model.dto.response.OfficeResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseResponse;
import com.ColPlat.Backend.model.entity.Company;

import java.util.List;

public interface WarehouseService {

    public void createWarehouse(CreateWarehouseRequest request, Company company);

    public List<WarehouseResponse> getAllWarehousesByCompany(Company company);

    public void updateWarehouse(Long warehouseId, CreateWarehouseRequest request);

    public void deleteWarehouse(Long warehouseId);
}
