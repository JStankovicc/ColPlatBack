package com.ColPlat.Backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user_profiles_change_request")
public class UserProfilesChangeRequest {
    @Id
    @GeneratedValue
    private Long id;
    private int basicFormer;
    private int standardFormer;
    private int premiumFormer;
    private int basicNew;
    private int standardNew;
    private int premiumNew;

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

    public int getBasicFormer() {
        return basicFormer;
    }

    public void setBasicFormer(int basicFormer) {
        this.basicFormer = basicFormer;
    }

    public int getStandardFormer() {
        return standardFormer;
    }

    public void setStandardFormer(int standardFormer) {
        this.standardFormer = standardFormer;
    }

    public int getPremiumFormer() {
        return premiumFormer;
    }

    public void setPremiumFormer(int premiumFormer) {
        this.premiumFormer = premiumFormer;
    }

    public int getBasicNew() {
        return basicNew;
    }

    public void setBasicNew(int basicNew) {
        this.basicNew = basicNew;
    }

    public int getStandardNew() {
        return standardNew;
    }

    public void setStandardNew(int standardNew) {
        this.standardNew = standardNew;
    }

    public int getPremiumNew() {
        return premiumNew;
    }

    public void setPremiumNew(int premiumNew) {
        this.premiumNew = premiumNew;
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
