package com.remcoil.skiron.database.repository;

import com.remcoil.skiron.database.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query("select e from Employee e")
    List<Employee> getAll();

    Optional<Employee> findByPhone(String phone);
}
