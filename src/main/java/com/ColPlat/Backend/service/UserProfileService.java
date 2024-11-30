package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.response.UserProfileResponse;

public interface UserProfileService {
    UserProfileResponse getUserProfileFromToken(String token);
}
