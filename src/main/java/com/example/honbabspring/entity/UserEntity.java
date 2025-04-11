package com.example.honbabspring.entity;

import com.example.honbabspring.type.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class UserEntity extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String username;

    private String password;

    private String confirmPassword;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    public UserEntity() {

    }

    public UserEntity(String username, String password, Role role) {
        this.userId = username;
        this.password = password;
        this.role = role;
    }
}
