package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCompany(Company companyFromToken);
}
