package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.response.StorageStateResponse;
import com.ColPlat.Backend.model.entity.InventoryItem;
import com.ColPlat.Backend.model.entity.Product;
import com.ColPlat.Backend.model.entity.StorageLocation;
import com.ColPlat.Backend.repository.InventoryItemRepository;
import com.ColPlat.Backend.repository.ProductRepository;
import com.ColPlat.Backend.repository.StorageLocationRepository;
import com.ColPlat.Backend.service.InventoryItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryItemServiceImplementation implements InventoryItemService {

    private final InventoryItemRepository inventoryItemRepository;
    private final ProductRepository productRepository;
    private final StorageLocationRepository storageLocationRepository;

    @Override
    @Transactional
    public void addInventory(Long locationId, Long productId, Double amount) {
        updateAmount(locationId, productId, amount);
    }

    @Override
    @Transactional
    public void subtractInventory(Long locationId, Long productId, Double amount) {
        updateAmount(locationId, productId, -amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StorageStateResponse> getAllInventory() {
        return inventoryItemRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StorageStateResponse> getInventoryByLocation(Long locationId) {
        StorageLocation location = storageLocationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Lokacija nije pronađena: " + locationId));

        return inventoryItemRepository.findByStorageLocation(location).stream()
                .map(this::mapToDTO)
                .toList();
    }

    private void updateAmount(Long locationId, Long productId, Double delta) {
        InventoryItem item = inventoryItemRepository
                .findByStorageLocationIdAndProductId(locationId, productId)
                .orElseGet(() -> {
                    if (delta < 0) throw new RuntimeException("Proizvod ne postoji na lokaciji, nemoguće oduzeti.");

                    return InventoryItem.builder()
                            .storageLocation(storageLocationRepository.getReferenceById(locationId))
                            .product(productRepository.getReferenceById(productId))
                            .amount(0.0)
                            .build();
                });

        double newAmount = item.getAmount() + delta;
        if (newAmount < 0) {
            throw new RuntimeException("Nedovoljno stanja na lokaciji. Trenutno: " + item.getAmount());
        }

        item.setAmount(newAmount);
        inventoryItemRepository.save(item);
    }

    private StorageStateResponse mapToDTO(InventoryItem item) {
        return StorageStateResponse.builder()
                .storageLocationId(item.getStorageLocation().getId())
                .productId(item.getProduct().getId())
                .amount(item.getAmount())
                .build();
    }
}
