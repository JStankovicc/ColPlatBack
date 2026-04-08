package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.AddWarehouseUserRequest;
import com.ColPlat.Backend.model.dto.request.CreateOfficeRequest;
import com.ColPlat.Backend.model.dto.request.CreateWarehouseRequest;
import com.ColPlat.Backend.model.dto.request.CreateWarehouseZoneRequest;
import com.ColPlat.Backend.model.dto.response.OfficeResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseUserResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseZoneResponse;
import com.ColPlat.Backend.model.enums.Role;
import com.ColPlat.Backend.model.enums.WarehouseRole;
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
    private final WarehouseUserService warehouseUserService;
    private final UserService userService;
    private final JwtService jwtService;

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
        return ResponseEntity.ok(warehouseService.getAllWarehouseResponsesByCompany(companyService.getCompanyFromToken(token)));
    }

    @GetMapping("/warehouse/my")
    public ResponseEntity<List<WarehouseResponse>> getMyWarehouses(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");
        if(userService.findByEmail(jwtService.extractUserName(token)).getRoles().contains(Role.ADMIN)){
            return ResponseEntity.ok(warehouseService.getAllWarehouseResponsesByCompany(companyService.getCompanyFromToken(token)));
        }
        return ResponseEntity.ok(warehouseUserService.getMyWarehouses(token));
    }

    @GetMapping("/warehouse/myRole")
    public ResponseEntity<WarehouseRole> getMyWarehouseRole(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseId){
        String token = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(warehouseUserService.getMyRole(token, warehouseId));
    }

    @GetMapping("/warehouse/users")
    public ResponseEntity<List<WarehouseUserResponse>> getAllWarehouseUsers(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseId){
        return ResponseEntity.ok(warehouseUserService.getAllWarehouseUserResponses(warehouseId));
    }

    @PostMapping("/warehouse/addOrChangeUser")
    public void addUserToWarehouse(@RequestHeader("Authorization") String authorizationHeader, @RequestBody AddWarehouseUserRequest addWarehouseUserRequest){
        warehouseUserService.saveOrUpdateUserWarehouse(addWarehouseUserRequest);
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
