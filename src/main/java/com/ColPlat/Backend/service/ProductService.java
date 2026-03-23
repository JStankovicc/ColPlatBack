package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.entity.Product;

public interface ProductService {
    Product findById(Long productId);
}
