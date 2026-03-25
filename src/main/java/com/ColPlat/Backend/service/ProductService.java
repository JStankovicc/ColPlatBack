package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.ProductRequest;
import com.ColPlat.Backend.model.dto.response.ProductResponse;
import com.ColPlat.Backend.model.entity.Product;

import java.util.List;

public interface ProductService {
    Product findById(Long productId);

    List<ProductResponse> findAllForCompany(String bearer);

    void save(ProductRequest productRequest, String bearer);

    void saveProductSupplier(Long supplierId, Long productId);

    ProductResponse getProductResponse(Product product);

    ProductResponse getProductResponseFromSKUOrBarcode(String code);
}
