package com.ColPlat.Backend.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierRequest {

    private Long id;
    private String name;
    private String contactName;
    private String email;
    private String phone;
    private String address;
    private String notes;

}
