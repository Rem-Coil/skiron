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
    @Query("delete from Product where productNumber > :ceilingNumber and batchId in :batchesIdList")
    void deleteWithHigherNumber(
            @Param("ceilingNumber") int ceilingNumber,
            @Param("batchesIdList") List<Long> batchesIdList
    );

    @Modifying
    @Query("delete from Product where batchId = :batchId and active = false")
    void deleteInactiveByBatch(@Param("batchId") long id);

    @Modifying
    @Query("update Product set locked = :status where id = :id")
    void setLockStatus(@Param("id") Long id, @Param("status") boolean status);

    List<Product> findAllByBatchId(Long id);
}
