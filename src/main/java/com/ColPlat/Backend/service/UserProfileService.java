package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.UserProfileWithPasswordRequest;
import com.ColPlat.Backend.model.dto.request.UserProfileWithoutPasswordRequest;
import com.ColPlat.Backend.model.dto.response.JwtAuthenticationResponse;
import com.ColPlat.Backend.model.dto.response.UserProfileResponse;
import com.ColPlat.Backend.model.dto.response.UserResponse;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.entity.UserProfile;

public interface UserProfileService {

    UserProfile getUserProfileById(Long id);

    UserProfileResponse getUserProfileFromToken(String token);

    void changeProfilePicture(String token, byte[] imageBytes);

    JwtAuthenticationResponse updateProfileWithoutPassword(String token, UserProfileWithoutPasswordRequest request);

    JwtAuthenticationResponse updateProfileWithPassword(String token, UserProfileWithPasswordRequest request);

    UserResponse getUserResponseFromUser(User u);
}
