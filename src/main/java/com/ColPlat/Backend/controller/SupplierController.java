package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.SupplierRequest;
import com.ColPlat.Backend.model.dto.response.SupplierResponse;
import com.ColPlat.Backend.model.entity.Supplier;
import com.ColPlat.Backend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @GetMapping("/forCompany")
    public ResponseEntity<List<SupplierResponse>> getAllSuppliersForCompany(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long companyId){
        return ResponseEntity.ok(supplierService.findAllByCompany(companyId));
    }

    @GetMapping("/byId")
    public ResponseEntity<Supplier> getSupplierById(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long supplierId){
        return ResponseEntity.ok(supplierService.findById(supplierId));
    }

    @PostMapping("/save")
    public void addSupplier(@RequestHeader("Authorization") String authorizationHeader, @RequestBody SupplierRequest supplierRequest){
        String token = authorizationHeader.replace("Bearer ","");
        supplierService.save(supplierRequest, token);
    }
}
