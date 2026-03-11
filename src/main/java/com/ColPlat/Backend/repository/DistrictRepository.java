package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    List<District> findAllByRegionId(int regionId);
}
