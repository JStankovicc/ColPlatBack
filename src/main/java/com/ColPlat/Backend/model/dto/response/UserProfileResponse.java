package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private String displayName;
    private String name;
    private byte[] profilePic;

}
