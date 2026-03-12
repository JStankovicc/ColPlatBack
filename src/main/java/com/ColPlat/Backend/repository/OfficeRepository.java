package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
    List<Office> findAllByCompany(Company company);
}
