package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.UserProfileWithPasswordRequest;
import com.ColPlat.Backend.model.dto.request.UserProfileWithoutPasswordRequest;
import com.ColPlat.Backend.model.dto.response.JwtAuthenticationResponse;
import com.ColPlat.Backend.model.dto.response.UserProfileResponse;

public interface UserProfileService {
    UserProfileResponse getUserProfileFromToken(String token);

    void changeProfilePicture(String token, byte[] imageBytes);

    JwtAuthenticationResponse updateProfileWithoutPassword(String token, UserProfileWithoutPasswordRequest request);

    JwtAuthenticationResponse updateProfileWithPassword(String token, UserProfileWithPasswordRequest request);

}
