package com.remcoil.skiron.database.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
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

    enum Role {
        ADMIN,
        QUALITY_ENGINEER,
        OPERATOR
    }
}
