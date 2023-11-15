package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.ControlAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlActionRepository extends JpaRepository<ControlAction, Long> {
}
