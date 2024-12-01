package com.ColPlat.Backend.model.entity;

import com.ColPlat.Backend.model.dto.response.CompanyResponse;
import com.ColPlat.Backend.utils.ImageUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.IOException;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_company")
public class Company {

    @Id
    private Long id;
    private String name;
    private String registryNum;
    private long locationId;
    @Lob
    @Column(name = "company_logo_pic", columnDefinition = "BLOB")
    private byte[] companyLogoPic;
    private Long billingDetailsId;
    private boolean termsAndConditionsAccepted;
    private String supportType;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CompanyResponse getCompanyResponse() throws IOException {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setCompanyName(this.name);
        companyResponse.setLogoPic(ImageUtils.getInstance().compressPngImageToThumbnail(this.companyLogoPic));
        return companyResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistryNum() {
        return registryNum;
    }

    public void setRegistryNum(String registryNum) {
        this.registryNum = registryNum;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long regionId) {
        this.locationId = regionId;
    }

    public byte[] getCompanyLogoPic() {
        return companyLogoPic;
    }

    public void setCompanyLogoPic(byte[] companyLogoPic) {
        this.companyLogoPic = companyLogoPic;
    }

    public Long getBillingDetailsId() {
        return billingDetailsId;
    }

    public void setBillingDetailsId(Long billingDetailsId) {
        this.billingDetailsId = billingDetailsId;
    }

    public boolean isTermsAndConditionsAccepted() {
        return termsAndConditionsAccepted;
    }

    public void setTermsAndConditionsAccepted(boolean termsAndConditionsAccepted) {
        this.termsAndConditionsAccepted = termsAndConditionsAccepted;
    }

    public String getSupportType() {
        return supportType;
    }

    public void setSupportType(String supportType) {
        this.supportType = supportType;
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
