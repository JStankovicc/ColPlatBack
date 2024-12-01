package com.ColPlat.Backend.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileWithPasswordRequest {
    private String displayName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}

