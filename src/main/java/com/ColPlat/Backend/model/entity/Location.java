package com.ColPlat.Backend.model.entity;

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
@Table(name = "_location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private short countryId;
    @Column(nullable = false)
    private Integer regionId;
    @Column(nullable = false)
    private Integer districtId;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String address;
}
