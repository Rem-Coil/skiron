package com.remcoil.skiron.model.employee;

import com.remcoil.skiron.database.entity.Employee;
import com.remcoil.skiron.database.entity.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

public record EmployeeFull(
        UUID id,
        String firstName,
        String lastName,
        String phone,
        String password,
        String confirmPassword,
        Boolean active,
        Role role
) {
    public Employee toEntity(PasswordEncoder passwordEncoder) {
        return new Employee(id, firstName, lastName, phone, passwordEncoder.encode(password), active, role);
    }

    public boolean passwordIsValid() {
        return password.equals(confirmPassword);
    }
}
