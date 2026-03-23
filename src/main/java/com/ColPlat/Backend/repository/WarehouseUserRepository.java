package com.ColPlat.Backend.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.ColPlat.Backend.model.entity.WarehouseUser;
import com.ColPlat.Backend.model.entity.WarehouseUserId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseUserRepository extends JpaRepository<WarehouseUser, WarehouseUserId> {
    Optional<WarehouseUser> findByIdWarehouseIdAndIdUserId(Long warehouseId, Long userId);

    List<WarehouseUser> findByIdUserId(Long userId);

    @EntityGraph(attributePaths = {"user"})
    List<WarehouseUser> findByIdWarehouseId(Long warehouseId);
}
