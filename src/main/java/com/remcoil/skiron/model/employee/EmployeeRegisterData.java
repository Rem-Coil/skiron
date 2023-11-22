package com.remcoil.skiron.model.employee;

import com.remcoil.skiron.database.entity.Employee;
import com.remcoil.skiron.exception.ForbiddenRoleException;
import org.springframework.security.crypto.password.PasswordEncoder;

public record EmployeeRegisterData(
        String firstName,
        String lastName,
        String phone,
        String password,
        String confirmPassword,
        Employee.Role role
) {
    public Employee toEntity(PasswordEncoder passwordEncoder) {
        if (role == Employee.Role.ADMIN) {
            throw new ForbiddenRoleException("Admin creation");
        }
        return new Employee(
                firstName,
                lastName,
                phone,
                passwordEncoder.encode(password),
                true,
                role
        );
    }

    public boolean passwordIsValid() {
        return password.equals(confirmPassword);
    }
}
