package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.OtherFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherFacilityRepository extends JpaRepository<OtherFacility, Integer> {
}
