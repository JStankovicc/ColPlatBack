package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Short> {
    Optional<Country> findByName(String name);
}
