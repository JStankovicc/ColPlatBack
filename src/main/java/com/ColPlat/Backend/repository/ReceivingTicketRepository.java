package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.dto.response.ReceivingTicketResponse;
import com.ColPlat.Backend.model.entity.ReceivingTicket;
import com.ColPlat.Backend.model.entity.StorageLocation;
import com.ColPlat.Backend.model.entity.WarehouseZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceivingTicketRepository extends JpaRepository<ReceivingTicket, Long> {

    List<ReceivingTicket> findAllByWarehouseZoneAndActive(WarehouseZone warehouseZone, boolean active);
}
