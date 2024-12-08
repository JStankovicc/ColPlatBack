package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    List<Region> findAllByCountryId(short id);
}
