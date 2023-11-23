package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    @Modifying
    @Query("delete from Action a where a.productId in :productsId")
    void deleteByProductsId(@Param("productsId") List<Long> productsId);
}
