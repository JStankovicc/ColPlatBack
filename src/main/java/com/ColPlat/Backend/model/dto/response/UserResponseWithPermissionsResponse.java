package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseWithPermissionsResponse {
    private Long id;
    private String displayName;
    private byte[] profilePic;
    Set<Role> roles;
}
