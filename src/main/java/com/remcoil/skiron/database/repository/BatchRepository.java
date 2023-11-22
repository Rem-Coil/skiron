package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    @Query("select b from Batch b where b.kitId = :kitId")
    List<Batch> findByKitId(@Param("kitId") Long kitId);
}
