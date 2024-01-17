package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.view.ExtendedControlAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtendedControlActionRepository extends JpaRepository<ExtendedControlAction, Long> {
    List<ExtendedControlAction> findAllByKitId(Long id);

    List<ExtendedControlAction> findAllByBatchId(Long id);
}
