package com.remcoil.skiron.model.employee;

import com.remcoil.skiron.database.entity.Employee;
import com.remcoil.skiron.database.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;


public record EmployeeDetails(
        UUID id,
        String phone,
        String password,
        Role role
) implements UserDetails {

    public static EmployeeDetails fromEntity(Employee employee) {
        return new EmployeeDetails(
                employee.getId(),
                employee.getPhone(),
                null,
                employee.getRole()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
