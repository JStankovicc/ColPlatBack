package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City,Integer> {
    List<City> findAllByRegionId(int id);
}
