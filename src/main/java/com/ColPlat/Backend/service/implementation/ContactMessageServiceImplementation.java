package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.ContactMessageRequest;
import com.ColPlat.Backend.model.dto.response.ContactMessageResponse;
import com.ColPlat.Backend.model.entity.ContactMessage;
import com.ColPlat.Backend.repository.ContactMessageRepository;
import com.ColPlat.Backend.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactMessageServiceImplementation implements ContactMessageService {
    private final ContactMessageRepository contactMessageRepository;
    @Override
    public void addMessage(ContactMessageRequest contactMessageRequest) {
        ContactMessage contactMessage = contactMessageRequest.getContactMessageFromContactMessageRequest();
        contactMessageRepository.save(contactMessage);
    }
}
