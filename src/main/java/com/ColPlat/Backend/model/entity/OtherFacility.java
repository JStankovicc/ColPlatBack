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
@DiscriminatorValue("OTHER")
public class OtherFacility extends Facility {
    private String purpose;

    @Column(columnDefinition = "TEXT")
    private String note;
}