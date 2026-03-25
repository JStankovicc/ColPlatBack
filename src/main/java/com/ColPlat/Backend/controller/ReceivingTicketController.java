package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.ReceivingTicketRequest;
import com.ColPlat.Backend.model.dto.response.ReceivingTicketResponse;
import com.ColPlat.Backend.service.ReceivingTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/receivingTicket")
@RequiredArgsConstructor
public class ReceivingTicketController {

    private final ReceivingTicketService receivingTicketService;

    @GetMapping("/byId")
    public ResponseEntity<ReceivingTicketResponse> findById(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long receivingTicketId){
        return ResponseEntity.ok(receivingTicketService.getById(receivingTicketId));
    }
    @GetMapping("/forWarehouseZone")
    public ResponseEntity<List<ReceivingTicketResponse>> getAllForWarehouse(@RequestHeader("Authorization") String authorizationHeader, @RequestParam Long warehouseZoneId){
        return ResponseEntity.ok(receivingTicketService.findAllByWarehouseZone(warehouseZoneId));
    }

    @PostMapping("/save")
    public void save(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ReceivingTicketRequest receivingTicketRequest){
        receivingTicketService.save(receivingTicketRequest);
    }

}
