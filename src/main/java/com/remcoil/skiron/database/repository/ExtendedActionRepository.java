package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.view.ExtendedAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtendedActionRepository extends JpaRepository<ExtendedAction, Long> {
    List<ExtendedAction> findAllByKitId(Long id);

    List<ExtendedAction> findAllByBatchId(Long id);
}
