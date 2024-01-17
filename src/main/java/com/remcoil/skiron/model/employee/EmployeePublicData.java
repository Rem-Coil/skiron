package com.remcoil.skiron.model.employee;

import com.remcoil.skiron.database.entity.Employee;
import com.remcoil.skiron.database.entity.Role;

import java.util.UUID;

public record EmployeePublicData(
        UUID id,
        String firstName,
        String lastName,
        String phone,
        Role role
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
