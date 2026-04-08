package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.response.WarehouseTaskResponse;
import com.ColPlat.Backend.model.entity.WarehouseTask;
import com.ColPlat.Backend.service.WarehouseTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouseTask")
@RequiredArgsConstructor
public class WarehouseTaskController {

    private final WarehouseTaskService warehouseTaskService;

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<WarehouseTaskResponse>> getAllWarehouseTasks(
            @RequestHeader("Authorization") String token,
            @PathVariable Long warehouseId) {
        return ResponseEntity.ok(warehouseTaskService.getAllWarehouseTasks(token, warehouseId));
    }

    @GetMapping("/my-tasks")
    public ResponseEntity<List<WarehouseTaskResponse>> getMyWarehouseTasks(
            @RequestHeader("Authorization") String token) {
        List<WarehouseTaskResponse> tasks = warehouseTaskService.getMyWarehouseTask(token.replace("Bearer ", ""));
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/complete/{taskId}")
    public ResponseEntity<Void> completeWarehouseTask(@PathVariable Long taskId) {
        warehouseTaskService.completeWarehouseTask(taskId);
        return ResponseEntity.ok().build();
    }
}
