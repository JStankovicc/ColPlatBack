package com.ColPlat.Backend.model.dto.request;

import com.ColPlat.Backend.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserRoleRequest {
    private Long userId;
    private Role role;
}
