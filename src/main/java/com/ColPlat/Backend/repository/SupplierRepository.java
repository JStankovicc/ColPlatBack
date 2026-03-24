package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findAllByCompany(Company byId);
}
