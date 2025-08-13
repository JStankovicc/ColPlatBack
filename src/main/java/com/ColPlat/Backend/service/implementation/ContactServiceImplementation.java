package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.response.ContactResponse;
import com.ColPlat.Backend.model.entity.Contact;
import com.ColPlat.Backend.model.entity.Team;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.repository.ContactListRepository;
import com.ColPlat.Backend.repository.ContactRepository;
import com.ColPlat.Backend.repository.TeamRepository;
import com.ColPlat.Backend.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImplementation implements ContactService {

    private final ContactRepository contactRepository;
    private final TeamRepository teamRepository;
    private final ContactListRepository contactListRepository;

    @Override
    public List<ContactResponse> findAllByUserId(Long userId) {
        List<Team> teams = teamRepository.findByUserIdsContaining(userId);

        return teams.stream()
                .flatMap(team -> contactListRepository.findByTeamId(team.getId()).stream())
                .flatMap(contactsList -> contactsList.getContacts().stream())
                .map(contact -> ContactResponse.builder()
                        .name(contact.getName())
                        .companyName(contact.getCompanyName())
                        .phoneNumber(contact.getPhoneNumber())
                        .email(contact.getEmail())
                        .status(contact.getStatus().toString())
                        .build())
                .distinct()
                .toList();
    }

    @Override
    public void addContact(User user, ContactResponse contactResponse) {

    }

    @Override
    public void deleteContact(User user, Long contactId) {

    }

    @Override
    public Contact findByEmail(String email) {
        return contactRepository.findByEmail(email);
    }

    @Override
    public void save(Contact contact) {
        contactRepository.save(contact);
    }

}
