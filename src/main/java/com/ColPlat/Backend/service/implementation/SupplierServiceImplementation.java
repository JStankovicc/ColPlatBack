package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.repository.SupplierRepository;
import com.ColPlat.Backend.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierServiceImplementation implements SupplierService {

    private final SupplierRepository supplierRepository;

}
