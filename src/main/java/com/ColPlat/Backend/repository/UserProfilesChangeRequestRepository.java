package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.UserProfilesChangeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfilesChangeRequestRepository extends JpaRepository<UserProfilesChangeRequest, Long> {
}
