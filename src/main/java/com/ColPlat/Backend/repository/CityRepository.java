package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City,Integer> {
    List<City> findAllByDistrictId(int id);

    Optional<City> getByName(String name);
}
