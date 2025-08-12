package com.ColPlat.Backend.model.dto.request;

import com.ColPlat.Backend.model.enums.ContactsListStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactsListsRequest {

    private String status;

}
