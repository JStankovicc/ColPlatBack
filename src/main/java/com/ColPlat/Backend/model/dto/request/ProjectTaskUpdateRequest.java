package com.ColPlat.Backend.model.dto.request;

import com.ColPlat.Backend.model.enums.TaskPriority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTaskUpdateRequest {
    private Long id;
    private String name;
    private String description;
    private Long projectId;
    private Date dateDue;
    private TaskPriority priority;
    private String status;
    private Long userId;
}