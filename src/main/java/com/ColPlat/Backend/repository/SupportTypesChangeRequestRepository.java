package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.dto.request.SupportTypesChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportTypesChangeRequestRepository extends JpaRepository<SupportTypesChangeRequest, Long> {
}
