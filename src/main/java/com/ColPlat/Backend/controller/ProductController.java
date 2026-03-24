package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.ProductRequest;
import com.ColPlat.Backend.model.dto.response.ProductResponse;
import com.ColPlat.Backend.model.entity.Product;
import com.ColPlat.Backend.model.enums.ProductType;
import com.ColPlat.Backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/getProductTypes")
    public ResponseEntity<List<String>> getAllProductTypes(@RequestHeader("Authorization") String authorizationHeader){
        return ResponseEntity.ok(Stream.of(ProductType.values())
                .map(Enum::name)
                .collect(Collectors.toList()));
    }

    @GetMapping("/forCompany")
    public ResponseEntity<List<ProductResponse>> getAllProductsForCompany(@RequestHeader("Authorization") String authorizationHeader){
        return ResponseEntity.ok(productService.findAllForCompany(authorizationHeader.replace("Bearer ","")));
    }

    @GetMapping("/byId")
    public ResponseEntity<Product> getProductById(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long productId){
        return ResponseEntity.ok(productService.findById(productId));
    }

    @PostMapping("/save")
    public void saveProduct(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProductRequest productRequest){
        productService.save(productRequest, authorizationHeader.replace("Bearer ", ""));
    }

    @PostMapping("/addSupplier")
    public void saveProductSupplier(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long supplierId, @RequestParam Long productId){
        productService.saveProductSupplier(supplierId, productId);
    }
}
