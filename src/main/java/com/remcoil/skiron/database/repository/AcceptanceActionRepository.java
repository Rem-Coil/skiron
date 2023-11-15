package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.AcceptanceAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcceptanceActionRepository extends JpaRepository<AcceptanceAction, Long> {
}
