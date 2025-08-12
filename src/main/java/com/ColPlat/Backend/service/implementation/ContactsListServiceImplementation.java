package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.response.ContactResponse;
import com.ColPlat.Backend.model.dto.response.ContactsListResponse;
import com.ColPlat.Backend.model.entity.City;
import com.ColPlat.Backend.model.entity.Contact;
import com.ColPlat.Backend.model.entity.Country;
import com.ColPlat.Backend.model.entity.Region;
import com.ColPlat.Backend.model.entity.Team;
import com.ColPlat.Backend.model.enums.ContactsListStatus;
import com.ColPlat.Backend.repository.ContactListRepository;
import com.ColPlat.Backend.service.*;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactsListServiceImplementation implements ContactListService {

    private final ContactListRepository contactListRepository;
    private final CountryService countryService;
    private final RegionService regionService;
    private final CityService cityService;
    private final TeamService teamService;

    @Override
    @Transactional
    public List<ContactsListResponse> getCompanyContacts(Long companyId, ContactsListStatus status) {

        return contactListRepository.findByCompanyIdAndStatus(companyId, status)
                .stream()
                .map(list -> ContactsListResponse.builder()
                        .name(list.getName())
                        .description(list.getDescription())
                        .team(Optional.ofNullable(teamService.getTeamById(list.getTeamId()))
                                .map(Team::getName)
                                .orElse("Unknown Team"))
                        .country(Optional.ofNullable(countryService.getCountryById(list.getCountryId()))
                                .map(Country::getName)
                                .orElse("Unknown Country"))
                        .region(Optional.ofNullable(regionService.getRegionById(list.getRegionId()))
                                .map(Region::getName)
                                .orElse("Unknown Region"))
                        .city(Optional.ofNullable(cityService.getCityById(list.getCityId()))
                                .map(City::getName)
                                .orElse("Unknown City"))
                        .contacts(list.getContacts().stream()
                                .map(Contact::getContactResponse)
                                .toList()
                        )
                        .build()
                )
                .toList();
    }
}
