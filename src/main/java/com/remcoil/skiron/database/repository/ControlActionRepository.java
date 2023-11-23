package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.ControlAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ControlActionRepository extends JpaRepository<ControlAction, Long> {
    @Modifying
    @Query("delete from ControlAction a where a.productId in :productsId")
    void deleteByProductsId(@Param("productsId") List<Long> productsId);

    List<ControlAction> findAllByProductId(Long productId);
}
