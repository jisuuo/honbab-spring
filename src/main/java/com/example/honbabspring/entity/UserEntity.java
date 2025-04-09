package com.example.honbabspring.entity;

import com.example.honbabspring.type.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
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

}
