package com.example.honbabspring.service;

import com.example.honbabspring.DTO.JoinDTO;
import com.example.honbabspring.entity.Role;
import com.example.honbabspring.entity.UserEntity;
import com.example.honbabspring.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public void join(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();
        String phone = joinDTO.getPhone();
        String roleString = joinDTO.getRole();

        Role role = Role.valueOf(roleString.toUpperCase());

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist) {
            return;
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setRole(role);

        userRepository.save(newUser);
    }
}
