package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.view.ExtendedAcceptanceAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtendedAcceptanceActionRepository extends JpaRepository<ExtendedAcceptanceAction, Long> {
    List<ExtendedAcceptanceAction> findAllByKitId(Long id);

    List<ExtendedAcceptanceAction> findAllByBatchId(Long id);
}
