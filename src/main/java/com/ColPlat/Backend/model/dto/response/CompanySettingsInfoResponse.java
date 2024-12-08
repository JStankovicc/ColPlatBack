package com.ColPlat.Backend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanySettingsInfoResponse {
    private String companyName;
    private String registrationNumber;
    private String address;
    private byte[] logoPic;
    private String country;
    private String region;
    private String city;
    private List<String> supportTypes;
    private int basicPackages;
    private int standardPackages;
    private int premiumPackages;
}
