package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.SignInRequest;
import com.ColPlat.Backend.model.dto.request.UserProfileWithPasswordRequest;
import com.ColPlat.Backend.model.dto.request.UserProfileWithoutPasswordRequest;
import com.ColPlat.Backend.model.dto.response.JwtAuthenticationResponse;
import com.ColPlat.Backend.model.dto.response.UserProfileResponse;
import com.ColPlat.Backend.model.dto.response.UserResponse;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.entity.UserProfile;
import com.ColPlat.Backend.repository.UserProfileRepository;
import com.ColPlat.Backend.repository.UserRepository;
import com.ColPlat.Backend.service.AuthenticationService;
import com.ColPlat.Backend.service.JwtService;
import com.ColPlat.Backend.service.UserProfileService;
import com.ColPlat.Backend.service.UserService;
import com.ColPlat.Backend.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImplementation implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfile getUserProfileById(Long id) {
        return userProfileRepository.findById(id).orElse(null);
    }

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
                        ImageUtils.getInstance().compressPngImageToThumbnail(profile.getProfilePic())
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

    @Override
    public void changeProfilePicture(String token, byte[] imageBytes) {
        String username = jwtService.extractUserName(token);
        User user = userService.findByEmail(username);
        Optional<UserProfile> userProfile = userProfileRepository.findById(user.getUserProfileId());

        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            profile.setProfilePic(imageBytes);
            userProfileRepository.save(profile);
        }
    }

    @Override
    public JwtAuthenticationResponse updateProfileWithoutPassword(String token, UserProfileWithoutPasswordRequest request) {
        String username = jwtService.extractUserName(token);
        User user = userService.findByEmail(username);
        Optional<UserProfile> userProfile = userProfileRepository.findById(user.getUserProfileId());

        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            profile.setDisplayName(request.getDisplayName());
            profile.setFirstName(request.getFirstName());
            profile.setLastName(request.getLastName());

            userProfileRepository.save(profile);

            user.setEmail(request.getEmail());
            userRepository.save(user);
        }

        String jwt = jwtService.generateToken(user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);

        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse updateProfileWithPassword(String token, UserProfileWithPasswordRequest request) {
        String username = jwtService.extractUserName(token);
        User user = userService.findByEmail(username);
        Optional<UserProfile> userProfile = userProfileRepository.findById(user.getUserProfileId());

        if (userProfile.isPresent()) {
            UserProfile profile = userProfile.get();
            profile.setDisplayName(request.getDisplayName());
            profile.setFirstName(request.getFirstName());
            profile.setLastName(request.getLastName());

            userProfileRepository.save(profile);

            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
        }


        return authenticationService.signIn(new SignInRequest(request.getEmail(), request.getPassword()));

    }

    @Override
    public UserResponse getUserResponseFromUser(User u) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findById(u.getUserProfileId());
        if(userProfileOptional.isPresent()){
            UserProfile userProfile = userProfileOptional.get();
            return UserResponse.builder().id(u.getId()).displayName(userProfile.getFirstName() + " " + userProfile.getLastName()).profilePic(userProfile.getProfilePic()).build();
        }
        return null;
    }
}
