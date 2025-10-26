package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Project;
import com.ColPlat.Backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByCompanyId(Long companyId);

    List<Project> findByUsersContaining(User user);
}
