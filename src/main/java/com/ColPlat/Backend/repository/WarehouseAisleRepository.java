package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.dto.response.WarehouseAisleResponse;
import com.ColPlat.Backend.model.entity.WarehouseAisle;
import com.ColPlat.Backend.model.entity.WarehouseZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseAisleRepository extends JpaRepository<WarehouseAisle, Long> {
    List<WarehouseAisle> findAllByZone(WarehouseZone zone);
}
