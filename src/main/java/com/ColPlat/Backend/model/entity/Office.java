package com.ColPlat.Backend.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("OFFICE")
public class Office extends Facility {
    private Integer maxDeskCapacity;
}