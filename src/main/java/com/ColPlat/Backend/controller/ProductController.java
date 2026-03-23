package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.enums.ProductType;
import com.ColPlat.Backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
