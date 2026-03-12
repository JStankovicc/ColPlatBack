package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    User findUserByEmail(String email);

    List<User> findAllByCompanyId(Long companyId);

    @Query("SELECT u FROM User u WHERE u.companyId = :companyId AND :role MEMBER OF u.roles")
    List<User> findAllByCompanyIdAndRole(@Param("companyId") Long companyId, @Param("role") Role role);

}