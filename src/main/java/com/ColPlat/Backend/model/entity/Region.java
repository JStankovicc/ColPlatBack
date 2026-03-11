package com.ColPlat.Backend.model.entity;

import com.ColPlat.Backend.model.dto.response.RegionResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private short countryId;
    private String name;

    public RegionResponse getRegionResponseFromRegion(){
        return RegionResponse.builder().id(this.id).name(this.name).build();
    }
}
