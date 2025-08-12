package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.response.TeamResponse;
import com.ColPlat.Backend.model.dto.response.UserProfileResponse;
import com.ColPlat.Backend.model.dto.response.UserResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Team;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.entity.UserProfile;
import com.ColPlat.Backend.model.enums.DepartmentType;
import com.ColPlat.Backend.repository.TeamRepository;
import com.ColPlat.Backend.service.TeamService;
import com.ColPlat.Backend.service.UserProfileService;
import com.ColPlat.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImplementation implements TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;
    private final UserProfileService userProfileService;

    @Override
    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TeamResponse> getAllSalesTeamsByCompany(Company company) {

        return teamRepository.findByCompanyIdAndDepartment_DepartmentType(company.getId(), DepartmentType.SALES)
                .stream()
                .map(team -> TeamResponse.builder()
                        .name(team.getName())
                        .description(team.getDescription())
                        .department(String.valueOf(team.getDepartment()))
                        .users(team.getUserIds().stream()
                                .map(userId -> {
                                    User user = userService.findById(userId);
                                    UserProfile profile = userProfileService.getUserProfileById(user.getUserProfileId());
                                    return UserProfileResponse.builder()
                                            .displayName(profile.getDisplayName())
                                            .name(profile.getFirstName() + " " + profile.getLastName())
                                            .profilePic(profile.getProfilePic())
                                            .build();
                                })
                                .collect(Collectors.toSet())
                        )
                        .build()
                )
                .toList();

    }
}
