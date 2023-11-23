package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.AcceptanceAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcceptanceActionRepository extends JpaRepository<AcceptanceAction, Long> {
    @Modifying
    @Query("delete from AcceptanceAction a where a.productId in :productsId")
    void deleteByProductsId(@Param("productsId") List<Long> productsId);
}
