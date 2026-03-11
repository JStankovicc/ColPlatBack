package com.ColPlat.Backend.model.entity;

import com.ColPlat.Backend.model.dto.response.CityResponse;
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
@Table(name = "_city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int districtId;
    @Column(unique = true, nullable = false)
    private String name;

    public CityResponse getCityResponse(){
        return CityResponse.builder().id(this.id).name(this.name).build();
    }

}
