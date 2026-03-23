package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.StorageLocation;
import com.ColPlat.Backend.model.entity.WarehouseShelf;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StorageLocationRepository extends JpaRepository<StorageLocation, Long> {
    List<StorageLocation> findAllByShelf(WarehouseShelf warehouseShelf);
}
