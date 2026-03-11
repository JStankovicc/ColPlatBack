package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Workstation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkstationRepository extends JpaRepository<Workstation, Integer> {
}
