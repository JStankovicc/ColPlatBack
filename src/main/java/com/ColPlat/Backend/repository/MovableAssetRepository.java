package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.MovableAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovableAssetRepository extends JpaRepository<MovableAsset, Long> {
    List<MovableAsset> findAllByCompany(Company company);
}
