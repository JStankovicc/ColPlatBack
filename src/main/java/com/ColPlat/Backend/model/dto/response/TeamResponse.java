package com.ColPlat.Backend.model.dto.response;


import com.ColPlat.Backend.model.entity.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {

    private String name;
    private String description;

    private String department;

    private Set<UserProfileResponse> users = new HashSet<>();
}
