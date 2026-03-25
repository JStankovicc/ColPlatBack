package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.ProductRequest;
import com.ColPlat.Backend.model.dto.response.ProductResponse;
import com.ColPlat.Backend.model.entity.Product;
import com.ColPlat.Backend.repository.ProductRepository;
import com.ColPlat.Backend.service.CompanyService;
import com.ColPlat.Backend.service.ProductService;
import com.ColPlat.Backend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService {

    private final ProductRepository productRepository;
    private final SupplierService supplierService;
    private final CompanyService companyService;

    @Override
    public Product findById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAllForCompany(String bearer) {
        List<Product> products = productRepository.findAllByCompany(companyService.getCompanyFromToken(bearer));
        List<ProductResponse> productResponses = new ArrayList<>();

        for(Product product : products){
            productResponses.add(getProductResponse(product));
        }

        return productResponses;
    }

    @Override
    @Transactional
    public void save(ProductRequest productRequest, String bearer) {
        Product product;
        if(productRequest.getId() != null){
            Optional<Product> productOptional = productRepository.findById(productRequest.getId());
            if(productOptional.isEmpty()){
                return;
            }
            product = productOptional.get();
        }else {
            product = new Product();
            product.setCompany(companyService.getCompanyFromToken(bearer));
        }

        product.setSku(productRequest.getSku());
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setBarcode(productRequest.getBarcode());
        product.setUnit(productRequest.getUnit());
        product.setCategory(productRequest.getCategory());
        product.setMinStockLevel(productRequest.getMinStockLevel());
        product.setReorderPoint(productRequest.getReorderPoint());
        product.setSupplier(supplierService.findById(productRequest.getSupplierId()));
        product.setProductType(productRequest.getProductType());

        product = productRepository.save(product);

        supplierService.addProduct(productRequest.getSupplierId(), product);
    }

    @Override
    public void saveProductSupplier(Long supplierId, Long productId){
        Product product = productRepository.findById(productId).orElse(null);
        if(product != null){
            supplierService.addProduct(supplierId, product);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .companyId(product.getCompany().getId())
                .sku(product.getSku())
                .description(product.getDescription())
                .barcode(product.getBarcode())
                .unit(product.getUnit())
                .category(product.getCategory())
                .minStockLevel(product.getMinStockLevel())
                .reorderPoint(product.getReorderPoint())
                .supplierResponse(supplierService.getSupplierResponse(product.getSupplier()))
                .productType(product.getProductType())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductResponseFromSKUOrBarcode(String code) {
        Optional<Product> productOptional = productRepository.findBySkuOrBarcode(code);
        return productOptional.map(this::getProductResponse).orElse(null);
    }
}
