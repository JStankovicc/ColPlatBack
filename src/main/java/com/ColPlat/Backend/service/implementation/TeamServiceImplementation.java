package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.entity.Team;
import com.ColPlat.Backend.repository.TeamRepository;
import com.ColPlat.Backend.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceImplementation implements TeamService {

    private final TeamRepository teamRepository;


    @Override
    public Team getTeamById(Long id) {
        return teamRepository.findById(id).orElse(null);
    }
}
