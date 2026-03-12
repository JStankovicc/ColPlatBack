package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    List<Warehouse> findAllByCompany(Company company);
}
