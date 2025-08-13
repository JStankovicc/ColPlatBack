package com.ColPlat.Backend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_task_note")
public class TaskNote {

    @Id
    @GeneratedValue
    private Long id;

    private String note;
    private Long userId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_task_id")  // ili kako je kolona u bazi
    private ProjectTask projectTask;
    private Date dateTime;
}
