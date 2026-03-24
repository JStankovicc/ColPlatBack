package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.SupplierRequest;
import com.ColPlat.Backend.model.dto.response.SupplierResponse;
import com.ColPlat.Backend.model.entity.Product;
import com.ColPlat.Backend.model.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<SupplierResponse> findAllByCompany(Long companyId);

    void save(SupplierRequest supplierRequest, String token);

    Supplier findById(Long supplierId);

    public SupplierResponse getSupplierResponse(Supplier supplier);

    void addProduct(Long supplierId, Product product);

}
