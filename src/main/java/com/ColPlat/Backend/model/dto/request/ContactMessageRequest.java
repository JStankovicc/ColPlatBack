package com.ColPlat.Backend.model.dto.request;


import com.ColPlat.Backend.model.entity.ContactMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactMessageRequest {

    private String email;
    private String sentBy;
    private String title;
    private String message;

    public ContactMessage getContactMessageFromContactMessageRequest(){
        ContactMessage contactMessage = new ContactMessage();
        contactMessage.setEmail(this.getEmail());
        contactMessage.setSentBy(this.getSentBy());
        contactMessage.setTitle(this.getTitle());
        contactMessage.setMessage(this.getMessage());
        contactMessage.setSeen(false);
        contactMessage.setResponded(false);
        contactMessage.setCreatedAt(LocalDateTime.now());

        return contactMessage;
    }

}
