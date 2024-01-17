package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.Kit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KitRepository extends JpaRepository<Kit, Long> {
    List<Kit> findAllBySpecificationId(Long specificationId);
}
