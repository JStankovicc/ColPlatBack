package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.CreateOfficeRequest;
import com.ColPlat.Backend.model.dto.request.CreateWarehouseRequest;
import com.ColPlat.Backend.model.dto.request.CreateWarehouseZoneRequest;
import com.ColPlat.Backend.model.dto.response.OfficeResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseZoneResponse;
import com.ColPlat.Backend.model.entity.Location;
import com.ColPlat.Backend.model.entity.Office;
import com.ColPlat.Backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/facility")
@RequiredArgsConstructor
public class FacilityController {

    private final OfficeService officeService;
    private final WarehouseService warehouseService;
    private final CompanyService companyService;
    private final WarehouseZoneService warehouseZoneService;

    @GetMapping("/office/all")
    public ResponseEntity<List<OfficeResponse>> getOfficesForCompany(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(officeService.getAllOfficesByCompany(companyService.getCompanyFromToken(token)));
    }

    @PostMapping("/office")
    public void addOffice(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CreateOfficeRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        officeService.createOffice(request, companyService.getCompanyFromToken(token));
    }

    @PutMapping("/office")
    public void updateOffice(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long officeId, @RequestBody CreateOfficeRequest request) {
        officeService.updateOffice(officeId, request);
    }

    @DeleteMapping("/office")
    public void deleteOffice(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long officeId) {
        officeService.deleteOffice(officeId);
    }

    @GetMapping("/warehouse/all")
    public ResponseEntity<List<WarehouseResponse>> getWarehousesForCompany(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(warehouseService.getAllWarehousesByCompany(companyService.getCompanyFromToken(token)));
    }

    @PostMapping("/warehouse")
    public void addWarehouse(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CreateWarehouseRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        warehouseService.createWarehouse(request, companyService.getCompanyFromToken(token));
    }

    @PutMapping("/warehouse")
    public void updateWarehouse(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseId, @RequestBody CreateWarehouseRequest request) {
        warehouseService.updateWarehouse(warehouseId, request);
    }

    @DeleteMapping("/warehouse")
    public void deleteWarehouse(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseId) {
        warehouseService.deleteWarehouse(warehouseId);
    }

    @GetMapping("/warehouse/zone/all")
    public ResponseEntity<List<WarehouseZoneResponse>> getWarehouseZonesForWarehouse(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseId){
        return ResponseEntity.ok(warehouseZoneService.getAllWarehouseZonesByWarehouse(warehouseService.findById(warehouseId)));
    }

    @PostMapping("/warehouse/zone")
    public void addWarehouseZone(@RequestHeader("Authorization") String authoprizationHeader, @RequestBody CreateWarehouseZoneRequest request){
        warehouseZoneService.createWarehouseZone(request);
    }

    @PutMapping("/warehouse/zone")
    public void updateWarehouseZone(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseZoneId, @RequestBody CreateWarehouseZoneRequest request){
        warehouseZoneService.updateWarehouseZone(warehouseZoneId, request);
    }

    @DeleteMapping("/warehouse/zone")
    public void deleteWarehouseZone(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseZoneId){
        warehouseZoneService.deleteWarehouseZone(warehouseZoneId);
    }
}
