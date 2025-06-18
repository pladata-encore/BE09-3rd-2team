package com.gp.nut.usermanagement.command.entity;

import com.gp.nut.usermanagement.command.dto.UserCreateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.EMPLOYEE;

    public void setEncodedPassword(String encode) {
        this.password = encode;
    }

    public void updateUser(String encodedPassword, String name) {
        this.password = encodedPassword;
        this.name = name;
    }

    public void updateRole(UserRole newRole) {
        this.role = newRole;
    }
}
