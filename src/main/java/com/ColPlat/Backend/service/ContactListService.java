package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.response.ContactsListResponse;
import com.ColPlat.Backend.model.enums.ContactsListStatus;

import java.util.List;

public interface ContactListService {

    public List<ContactsListResponse> getCompanyContacts(Long companyId, ContactsListStatus status);
}
