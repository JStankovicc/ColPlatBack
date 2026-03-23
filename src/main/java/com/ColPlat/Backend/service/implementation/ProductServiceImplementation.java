package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.repository.ProductRepository;
import com.ColPlat.Backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;


}
