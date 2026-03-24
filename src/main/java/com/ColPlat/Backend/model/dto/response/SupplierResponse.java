package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.entity.Company;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponse {

    private Long id;

    private Long companyId;

    private String name;

    private String contactName;

    private String email;

    private String phone;

    private String address;

    private String notes;
}
