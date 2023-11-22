package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Modifying
    @Query("delete from Product p where p.productNumber > :ceilingNumber and p.batchId in :batchesIdList")
    void deleteWithHigherNumber(
            @Param("ceilingNumber") int ceilingNumber,
            @Param("batchesIdList") List<Long> batchesIdList
    );
}
