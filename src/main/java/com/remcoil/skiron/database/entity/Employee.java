package com.remcoil.skiron.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "employees")
public class Employee {
    @Id
    private UUID id = UUID.randomUUID();

    private String firstName;
    private String lastName;
    private String phone;
    private String password;
    private Boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role implements GrantedAuthority {
        OPERATOR,
        QUALITY_ENGINEER,
        ADMIN;

        @Override
        public String getAuthority() {
            return "ROLE_" + this.name();
        }
    }

    public Employee(String firstName, String lastName, String phone, String password, Boolean active, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.password = password;
        this.active = active;
        this.role = role;
    }
}
