package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.response.ContactResponse;
import com.ColPlat.Backend.model.entity.Contact;
import com.ColPlat.Backend.model.entity.User;

import java.util.List;

public interface ContactService {
    List<ContactResponse> findAllByUserId(Long id);
    void addContact(User user, ContactResponse contactResponse);
    void deleteContact(User user, Long contactId);
    Contact findByEmail(String email);
    void save(Contact contact);
}
