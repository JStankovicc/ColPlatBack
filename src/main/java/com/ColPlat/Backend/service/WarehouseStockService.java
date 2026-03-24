package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.WarehouseStockRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseStockResponse;
import com.ColPlat.Backend.model.entity.WarehouseStock;

import java.util.List;

public interface WarehouseStockService {

    public WarehouseStockResponse getWarehouseStockResponse(Long warehouseId, Long productId);

    public List<WarehouseStockResponse> getAllWarehouseStock(Long warehouseId);

    public void save(WarehouseStockRequest warehouseStockRequest);

    public Double changeWarehouseStockAmount(Long warehouseStockId, Double amountChange);

    public WarehouseStockResponse getWarehouseStockResponse(WarehouseStock warehouseStock);

}
