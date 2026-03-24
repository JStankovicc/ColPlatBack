package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.WarehouseStockRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseStockResponse;
import com.ColPlat.Backend.service.WarehouseService;
import com.ColPlat.Backend.service.WarehouseStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouseStock")
@RequiredArgsConstructor
public class WarehouseStockController {

    private final WarehouseStockService warehouseStockService;

    @GetMapping("/")
    public ResponseEntity<WarehouseStockResponse> getWarehouseStock(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseId, @RequestParam Long productId){
        return ResponseEntity.ok(warehouseStockService.getWarehouseStockResponse(warehouseId, productId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<WarehouseStockResponse>> getWarehouseStock(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseId){
        return ResponseEntity.ok(warehouseStockService.getAllWarehouseStock(warehouseId));
    }

    @PostMapping("/save")
    public void saveWarehouseStock(@RequestHeader("Authorization") String authorizationHeader, @RequestBody WarehouseStockRequest warehouseStockRequest){
        warehouseStockService.save(warehouseStockRequest);
    }

    @PostMapping("/changeAmount")
    public ResponseEntity<Double> updateWarehouseStockAmount(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseStockId, @RequestParam Double amountChange){
        return ResponseEntity.ok(warehouseStockService.changeWarehouseStockAmount(warehouseStockId, amountChange));
    }

}
