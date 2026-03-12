package com.ColPlat.Backend.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateWarehouseRequest {

    private String name;

    private String code;

    private LocalTime openAt;
    private LocalTime closedAt;
    private Long managerId;


    private short countryId;
    private Integer regionId;
    private Integer districtId;
    private String city;
    private String address;


}
