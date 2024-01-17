package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {
    @Query("select o from OperationType o where o.specification.id = :specificationId")
    List<OperationType> findAllBySpecificationId(@Param("specificationId") Long specificationId);
}
