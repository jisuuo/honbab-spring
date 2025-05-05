package com.example.honbabspring.user.entity;

import com.example.honbabspring.global.entity.BaseTime;
import com.example.honbabspring.user.type.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@Table(name = "user")
public class User extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    private String username;

    private String password;

    private String confirmPassword;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {

    }

    public User(String username, String password, Role role) {
        this.userId = username;
        this.password = password;
        this.role = role;
    }

}
