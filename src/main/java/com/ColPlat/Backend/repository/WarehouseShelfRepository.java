package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.WarehouseAisle;
import com.ColPlat.Backend.model.entity.WarehouseShelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseShelfRepository extends JpaRepository<WarehouseShelf, Long> {
    List<WarehouseShelf> findAllByAisle(WarehouseAisle warehouseAisle);
}
