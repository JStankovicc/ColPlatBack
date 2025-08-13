package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Team;
import com.ColPlat.Backend.model.enums.DepartmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Collection<Team> findByCompanyIdAndDepartment_DepartmentType(Long companyId, DepartmentType departmentType);

    List<Team> findByUserIdsContaining(Long userId);
}
