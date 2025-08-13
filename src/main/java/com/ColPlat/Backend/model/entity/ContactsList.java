package com.ColPlat.Backend.model.entity;

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
@Entity
@Table(name = "_contacts_list")
public class ContactsList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private Long companyId;
    private Long teamId;

    private Short countryId;
    private Integer regionId;
    private Integer cityId;

    @Enumerated(EnumType.STRING)
    private ContactsListStatus status;

    @OneToMany(mappedBy = "contactsList", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Contact> contacts = new ArrayList<>();
}
