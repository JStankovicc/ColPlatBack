package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Location;
import com.ColPlat.Backend.model.entity.Workstation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfficeResponse {

    private Long id;
    private String name;
    private String code;
    private LocalTime openAt;
    private LocalTime closedAt;

    //workstations

    private Long locationId;
    private Integer maxDeskCapacity;
}
