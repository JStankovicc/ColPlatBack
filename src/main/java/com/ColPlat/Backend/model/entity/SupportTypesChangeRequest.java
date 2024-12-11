package com.ColPlat.Backend.model.entity;

import com.ColPlat.Backend.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_support_types_change_request")
public class SupportTypesChangeRequest {
    @Id
    private Long id;
    private String oldSupportTypes;
    private String newSupportTypes;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getOldSupportTypes() {
        return oldSupportTypes;
    }

    public void setOldSupportTypes(String oldSupportTypes) {
        this.oldSupportTypes = oldSupportTypes;
    }

    public String getNewSupportTypes() {
        return newSupportTypes;
    }

    public void setNewSupportTypes(String newSupportTypes) {
        this.newSupportTypes = newSupportTypes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
