package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.ContactMessageRequest;
import com.ColPlat.Backend.model.dto.response.ContactMessageResponse;

public interface ContactMessageService {
    void addMessage(ContactMessageRequest contactMessageRequest);
}
