package com.remcoil.skiron.model.employee;

import com.remcoil.skiron.database.entity.Employee;

import java.util.UUID;

public record EmployeePublicData(
        UUID id,
        String firstName,
        String lastName,
        String phone,
        Employee.Role role
) {
    public static EmployeePublicData fromEntity(Employee employee) {
        return new EmployeePublicData(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPhone(),
                employee.getRole()
        );
    }
}
