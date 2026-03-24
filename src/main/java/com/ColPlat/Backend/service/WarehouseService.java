package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.CreateWarehouseRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Warehouse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface WarehouseService {

    public void createWarehouse(CreateWarehouseRequest request, Company company);

    public List<WarehouseResponse> getAllWarehouseResponsesByCompany(Company company);

    public List<Warehouse> getAllWarehousesByCompany(Company company);

    public void updateWarehouse(Long warehouseId, CreateWarehouseRequest request);

    public void deleteWarehouse(Long warehouseId);

    public Warehouse findById(Long warehouseId);

    boolean existsById(Long warehouseId);

    WarehouseResponse getWarehouseResponse(Warehouse warehouse);
}
