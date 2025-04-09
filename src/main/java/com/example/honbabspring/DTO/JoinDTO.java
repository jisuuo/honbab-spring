package com.example.honbabspring.DTO;

import com.example.honbabspring.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDTO {
    private String username;
    private String password;
    private String phone;
    private String email;
    private String role;
}
