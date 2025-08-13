package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.TaskStatus;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {
    List<TaskStatus> findAllByProjectId(Long projectId);
}
