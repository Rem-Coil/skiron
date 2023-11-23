package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    @Modifying
    @Query("update Employee set active = :status where id = :id")
    void updateActiveStatus(@Param("id") UUID id, @Param("status") boolean status);

    @Modifying
    @Query("update Employee set role = :role where id = :id")
    void updateRole(@Param("id") UUID id, @Param("role") Employee.Role role);

    Optional<Employee> findByPhone(String phone);
}
