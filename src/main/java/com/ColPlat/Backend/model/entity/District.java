package com.ColPlat.Backend.model.entity;

import com.ColPlat.Backend.model.dto.response.DistrictResponse;
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
@Table(name = "_district")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int regionId;
    @Column(unique = true, nullable = false)
    private String name;

    public DistrictResponse getDistrictResponse() {
        return DistrictResponse.builder().id(this.id).name(this.name).build();
    }
}
