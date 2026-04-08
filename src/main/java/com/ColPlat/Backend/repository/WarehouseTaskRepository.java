package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.entity.WarehouseTask;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseTaskRepository extends JpaRepository<WarehouseTask, Long> {
    List<WarehouseTask> findAllByCompletedFalse();

    List<WarehouseTask> findAllByAssignedUserAndCompletedFalse(User user);

    @EntityGraph(attributePaths = {"assignedUser", "sourceLocation", "destinationLocation"})
    List<WarehouseTask> findAllByAssignedUserInAndCompletedFalse(List<User> users);
}
