package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.ReceivingTicketApprovalRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseTaskResponse;
import com.ColPlat.Backend.model.entity.ReceivingTicket;
import com.ColPlat.Backend.model.entity.WarehouseTask;

import java.util.List;

public interface WarehouseTaskService {

    public void createTaskFromTicket(ReceivingTicket ticket, ReceivingTicketApprovalRequest request);

    public List<WarehouseTaskResponse> getAllWarehouseTasks(String token, Long warehouseId);

    public List<WarehouseTaskResponse> getMyWarehouseTask(String jwt);

    public void completeWarehouseTask(Long warehouseTaskId);
}
