package com.ColPlat.Backend.model.entity;

import com.ColPlat.Backend.model.dto.response.ContactResponse;
import com.ColPlat.Backend.model.enums.ContactStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String companyName;
    private String phoneNumber;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contacts_list_id", nullable = false)
    private ContactsList contactsList;

    @Enumerated(EnumType.STRING)
    private ContactStatus status;

    public ContactResponse getContactResponse() {
        ContactResponse contactResponse = new ContactResponse();
        contactResponse.setName(name);
        contactResponse.setCompanyName(companyName);
        contactResponse.setPhoneNumber(phoneNumber);
        contactResponse.setEmail(email);
        contactResponse.setStatus(status.name());
        return contactResponse;
    }

}