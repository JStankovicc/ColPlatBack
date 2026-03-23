package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.StorageLocationRequest;
import com.ColPlat.Backend.model.dto.response.StorageLocationResponse;
import com.ColPlat.Backend.model.enums.ProductType;

import java.util.List;

public interface StorageLocationService {


    List<StorageLocationResponse> getAllForWarehouseShelf(Long warehouseShelfId);

    void save(StorageLocationRequest storageLocationRequest);
}
