package com.ColPlat.Backend.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponse {

    private Long id;
    private String name;
    private String code;
    private LocalTime openAt;
    private LocalTime closedAt;

    //workstations

    private Long locationId;
    private String location;
    private UserResponse manager;

}
