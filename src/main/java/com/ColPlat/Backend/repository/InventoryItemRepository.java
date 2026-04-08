package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.InventoryItem;
import com.ColPlat.Backend.model.entity.StorageLocation;
import io.micrometer.common.KeyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByStorageLocationIdAndProductId(Long locationId, Long productId);

    List<InventoryItem> findByStorageLocation(StorageLocation storageLocation);
}
