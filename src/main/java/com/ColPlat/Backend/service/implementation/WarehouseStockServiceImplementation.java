package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.WarehouseStockRequest;
import com.ColPlat.Backend.model.dto.response.ProductResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseStockResponse;
import com.ColPlat.Backend.model.entity.Product;
import com.ColPlat.Backend.model.entity.Warehouse;
import com.ColPlat.Backend.model.entity.WarehouseStock;
import com.ColPlat.Backend.repository.WarehouseStockRepository;
import com.ColPlat.Backend.service.ProductService;
import com.ColPlat.Backend.service.WarehouseService;
import com.ColPlat.Backend.service.WarehouseStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseStockServiceImplementation implements WarehouseStockService {

    private final WarehouseStockRepository warehouseStockRepository;
    private final WarehouseService warehouseService;
    private final ProductService productService;

    @Override
    @Transactional(readOnly = true)
    public WarehouseStockResponse getWarehouseStockResponse(Long warehouseId, Long productId) {
        Warehouse warehouse = warehouseService.findById(warehouseId);
        Product product = productService.findById(productId);
        Optional<WarehouseStock> warehouseStockOptional = warehouseStockRepository.findByWarehouseAndProduct(warehouse, product);
        return warehouseStockOptional.map(this::getWarehouseStockResponse).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseStockResponse> getAllWarehouseStock(Long warehouseId) {
        Warehouse warehouse = warehouseService.findById(warehouseId);
        List<WarehouseStock> warehouseStocks = warehouseStockRepository.findAllByWarehouse(warehouse);
        List<WarehouseStockResponse> warehouseStockResponses = new ArrayList<>();
        for(WarehouseStock warehouseStock : warehouseStocks){
            warehouseStockResponses.add(getWarehouseStockResponse(warehouseStock));
        }
        return warehouseStockResponses;
    }

    @Override
    @Transactional
    public void save(WarehouseStockRequest warehouseStockRequest) {
        Warehouse warehouse = warehouseService.findById(warehouseStockRequest.getWarehouseId());
        Product product = productService.findById(warehouseStockRequest.getProductId());
        WarehouseStock warehouseStock;
        if(warehouseStockRequest.getId() != null){
            Optional<WarehouseStock> warehouseStockOptional = warehouseStockRepository.findById(warehouseStockRequest.getId());
            if(warehouseStockOptional.isEmpty()) return;
            warehouseStock = warehouseStockOptional.get();
        }else warehouseStock = new WarehouseStock();

        warehouseStock.setWarehouse(warehouse);
        warehouseStock.setProduct(product);
        warehouseStock.setAmount(warehouseStockRequest.getAmount());

        warehouseStockRepository.save(warehouseStock);
    }

    @Override
    @Transactional
    public Double changeWarehouseStockAmount(Long warehouseStockId, Double amountChange) {
        Optional<WarehouseStock> warehouseStockOptional = warehouseStockRepository.findById(warehouseStockId);
        if(warehouseStockOptional.isEmpty()) return null;
        WarehouseStock warehouseStock = warehouseStockOptional.get();
        warehouseStock.setAmount(warehouseStock.getAmount() + amountChange);
        warehouseStock = warehouseStockRepository.save(warehouseStock);
        return warehouseStock.getAmount();
    }

    @Override
    @Transactional(readOnly = true)
    public WarehouseStockResponse getWarehouseStockResponse(WarehouseStock warehouseStock){
        WarehouseResponse warehouseResponse = warehouseService.getWarehouseResponse(warehouseStock.getWarehouse());
        ProductResponse productResponse = productService.getProductResponse(warehouseStock.getProduct());

        return WarehouseStockResponse.builder()
                .id(warehouseStock.getId())
                .warehouseResponse(warehouseResponse)
                .productResponse(productResponse)
                .amount(warehouseStock.getAmount())
                .build();
    }

}
