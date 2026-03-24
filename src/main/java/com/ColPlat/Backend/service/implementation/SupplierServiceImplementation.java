package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.SupplierRequest;
import com.ColPlat.Backend.model.dto.response.SupplierResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Product;
import com.ColPlat.Backend.model.entity.Supplier;
import com.ColPlat.Backend.repository.SupplierRepository;
import com.ColPlat.Backend.service.CompanyService;
import com.ColPlat.Backend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierServiceImplementation implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final CompanyService companyService;

    @Override
    @Transactional
    public List<SupplierResponse> findAllByCompany(Long companyId) {
        List<Supplier> suppliers = supplierRepository.findAllByCompany(companyService.findById(companyId));
        List<SupplierResponse> supplierResponses = new ArrayList<>();
        for(Supplier supplier : suppliers){
            supplierResponses.add(getSupplierResponse(supplier));
        }
        return supplierResponses;
    }

    @Override
    @Transactional
    public void save(SupplierRequest supplierRequest, String token) {
        Supplier supplier;
        if(supplierRequest.getId() != null){
            Optional<Supplier> supplierOptional = supplierRepository.findById(supplierRequest.getId());
            if(supplierOptional.isPresent()){
                supplier = supplierOptional.get();
            }else {
                return;
            }
        }else {
            supplier = new Supplier();
            supplier.setCompany(companyService.getCompanyFromToken(token));
        }

        supplier.setName(supplierRequest.getName());
        supplier.setContactName(supplierRequest.getContactName());
        supplier.setEmail(supplierRequest.getEmail());
        supplier.setPhone(supplierRequest.getPhone());
        supplier.setAddress(supplierRequest.getAddress());
        supplier.setNotes(supplierRequest.getNotes());

        supplierRepository.save(supplier);

    }

    @Override
    public Supplier findById(Long supplierId) {
        return supplierRepository.findById(supplierId).orElse(null);
    }

    @Override
    public SupplierResponse getSupplierResponse(Supplier supplier){
        return SupplierResponse.builder()
                .id(supplier.getId())
                .companyId(supplier.getCompany().getId())
                .name(supplier.getName())
                .contactName(supplier.getContactName())
                .email(supplier.getEmail())
                .phone(supplier.getPhone())
                .address(supplier.getAddress())
                .notes(supplier.getNotes())
                .build();
    }

    @Override
    @Transactional
    public void addProduct(Long supplierId, Product product) {
        Optional<Supplier> supplierOptional = supplierRepository.findById(supplierId);
        if(supplierOptional.isEmpty()){
            return;
        }
        Supplier supplier = supplierOptional.get();
        List<Product> products = supplier.getProducts();
        products.add(product);
        supplier.setProducts(products);

        supplierRepository.save(supplier);
    }
}
