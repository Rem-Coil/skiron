package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecificationRepository extends JpaRepository<Specification, Long> {

    @Query("select s from Specification s join fetch s.operationTypes where s.id = :id")
    Optional<Specification> findByIdAndFetch(@Param("id") Long id);

    @Query("select s from Specification s join fetch s.operationTypes")
    List<Specification> findAllAndFetch();
}

