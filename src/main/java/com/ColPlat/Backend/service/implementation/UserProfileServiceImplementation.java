package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.response.UserProfileResponse;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.entity.UserProfile;
import com.ColPlat.Backend.repository.UserProfileRepository;
import com.ColPlat.Backend.service.JwtService;
import com.ColPlat.Backend.service.UserProfileService;
import com.ColPlat.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImplementation implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public UserProfileResponse getUserProfileFromToken(String token) {
        String username = jwtService.extractUserName(token);
        User user = userService.findByEmail(username);
        Optional<UserProfile> userProfile = userProfileRepository.findById(user.getUserProfileId());

        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            try {
                return new UserProfileResponse(
                        profile.getDisplayName(),
                        profile.getFirstName() + " " + profile.getLastName(),
                        compressImage(profile.getProfilePic())
                );
            } catch (IOException e) {
                return new UserProfileResponse(
                        profile.getDisplayName(),
                        profile.getFirstName() + " " + profile.getLastName(),
                        null
                );
            }
        }
        return null;

    }

    public byte[] compressImage(byte[] originalImage) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(originalImage);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(inputStream)
                .size(200, 200)
                .outputFormat("png")
                .toOutputStream(outputStream);

        return outputStream.toByteArray();
    }

}
