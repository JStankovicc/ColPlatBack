package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.AddWarehouseUserRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseUserResponse;
import com.ColPlat.Backend.model.enums.WarehouseRole;

import java.util.List;

public interface WarehouseUserService {

    public WarehouseRole getMyRole(String token, Long warehouseId);

    public List<WarehouseResponse> getMyWarehouses(String token);

    public void saveOrUpdateUserWarehouse(AddWarehouseUserRequest addWarehouseUserRequest);

    List<WarehouseUserResponse> getAllWarehouseUsers(Long warehouseId);
}
