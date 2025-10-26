package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.dto.request.UserProfilesChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfilesChangeRequestRepository extends JpaRepository<UserProfilesChangeRequest, Long> {
}
