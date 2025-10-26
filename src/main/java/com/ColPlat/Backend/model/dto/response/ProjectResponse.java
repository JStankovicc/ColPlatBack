package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.entity.ProjectTask;
import com.ColPlat.Backend.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {

    private Long id;

    private String name;
    private String description;
    private CompanyResponse company;
    private Long teamId;

    private List<ProjectTask> projectTasks;

    //project event

    private String note;

    //notifications

    private Date createdAt;

    private Date updatedAt;

    private Date startDate;

    private Set<UserResponse> users;
}
