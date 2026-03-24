package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Product;
import com.ColPlat.Backend.model.entity.Warehouse;
import com.ColPlat.Backend.model.entity.WarehouseStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseStockRepository extends JpaRepository<WarehouseStock, Long> {
    Optional<WarehouseStock> findByWarehouseAndProduct(Warehouse warehouse, Product product);

    List<WarehouseStock> findAllByWarehouse(Warehouse warehouse);
}
