package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.entity.Contact;
import com.ColPlat.Backend.model.enums.ContactsListStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactsListResponse {

    private String name;
    private String description;

    private String team;

    private String country;
    private String region;
    private String city;


    private List<ContactResponse> contacts = new ArrayList<>();

}
