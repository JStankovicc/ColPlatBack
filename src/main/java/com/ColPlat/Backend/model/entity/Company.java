package com.ColPlat.Backend.model.entity;

import com.ColPlat.Backend.model.dto.response.CompanyResponse;
import com.ColPlat.Backend.model.enums.SupportType;
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
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String registryNum;
    private long locationId;
    @Lob
    @Column(name = "company_logo_pic", columnDefinition = "BLOB")
    private byte[] companyLogoPic;
    private Long billingDetailsId;
    private boolean termsAndConditionsAccepted;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "_company_support",
            joinColumns = @JoinColumn(name = "company_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<SupportType> supportTypes;
    private int numberOfProfiles;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public CompanyResponse getCompanyResponse() throws IOException {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setId(this.id);
        companyResponse.setCompanyName(this.name);
        companyResponse.setLogoPic(ImageUtils.getInstance().compressPngImageToThumbnail(this.companyLogoPic));
        return companyResponse;
    }

}
