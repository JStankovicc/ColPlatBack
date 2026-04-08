package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.ReceivingTicketApprovalRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseTaskResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseUserResponse;
import com.ColPlat.Backend.model.entity.*;
import com.ColPlat.Backend.repository.StorageLocationRepository;
import com.ColPlat.Backend.repository.UserRepository;
import com.ColPlat.Backend.repository.WarehouseTaskRepository;
import com.ColPlat.Backend.service.JwtService;
import com.ColPlat.Backend.service.UserService;
import com.ColPlat.Backend.service.WarehouseTaskService;
import com.ColPlat.Backend.service.WarehouseUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseTaskServiceImplementation implements WarehouseTaskService {

    private final WarehouseTaskRepository warehouseTaskRepository;
    private final UserRepository userRepository;
    private final StorageLocationRepository storageLocationRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final WarehouseUserService warehouseUserService;

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseTaskResponse> getAllWarehouseTasks(String token, Long warehouseId) {
        List<WarehouseUser> warehouseUsers = warehouseUserService.getAllWarehouseUsers(warehouseId);

        if (warehouseUsers.isEmpty()) {
            return Collections.emptyList();
        }

        List<User> usersInWarehouse = warehouseUsers.stream()
                .map(WarehouseUser::getUser)
                .toList();

        List<WarehouseTask> tasks = warehouseTaskRepository.findAllByAssignedUserInAndCompletedFalse(usersInWarehouse);

        return tasks.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseTaskResponse> getMyWarehouseTask(String jwt) {
        User user = userService.findByEmail(jwtService.extractUserName(jwt));

        List<WarehouseTask> tasks = warehouseTaskRepository
                .findAllByAssignedUserAndCompletedFalse(user);

        return tasks.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void completeWarehouseTask(Long warehouseTaskId) {
        WarehouseTask task = warehouseTaskRepository.findById(warehouseTaskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (task.isCompleted()) {
            throw new IllegalStateException("Task is already completed.");
        }

        task.setCompleted(true);

        warehouseTaskRepository.save(task);
    }


    @Override
    @Transactional
    public void createTaskFromTicket(ReceivingTicket ticket, ReceivingTicketApprovalRequest request) {
        User executedBy = userRepository.findById(request.getExecutedById())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        StorageLocation destination = storageLocationRepository.findById(request.getNewStorageLocationId())
                .orElseThrow(() -> new EntityNotFoundException("Storage location not found"));

        WarehouseTask task = WarehouseTask.builder()
                .assignedUser(executedBy)
                .sourceLocation(null)
                .destinationLocation(destination)
                .items(new ArrayList<>())
                .completed(false)
                .build();

        WarehouseTask savedTask = warehouseTaskRepository.saveAndFlush(task);

        List<WarehouseTask.ProductAmount> taskItems = ticket.getProductAmount().stream()
                .map(item -> WarehouseTask.ProductAmount.builder()
                        .productId(item.getProduct().getId())
                        .amount(item.getAmount())
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));

        savedTask.setItems(taskItems);

        warehouseTaskRepository.save(savedTask);
    }

    private WarehouseTaskResponse mapToResponse(WarehouseTask task) {
        return WarehouseTaskResponse.builder()
                .id(task.getId())
                .assignedUserId(task.getAssignedUser().getId())
                .sourceLocationId(task.getSourceLocation() != null ? task.getSourceLocation().getId() : null)
                .sourceLocationName(task.getSourceLocation() != null ? task.getSourceLocation().getName() : null)
                .destinationLocationId(task.getDestinationLocation().getId())
                .destinationLocationName(task.getDestinationLocation().getName())
                .items(new ArrayList<>(task.getItems()))
                .completed(task.isCompleted())
                .createdAt(task.getCreatedAt())
                .build();
    }
}

