package com.example.honbabspring.service;

import com.example.honbabspring.dto.JoinDTO;
import com.example.honbabspring.type.Role;
import com.example.honbabspring.entity.UserEntity;
import com.example.honbabspring.exception.DuplicateException;
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
        String userId = joinDTO.getUserId();
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();
        String phone = joinDTO.getPhone();
        String roleString = joinDTO.getRole();

        Role role = Role.valueOf(roleString.toUpperCase());

        boolean isExistUserId = userRepository.existsByUserId(userId);
        if (isExistUserId) {
            throw new DuplicateException("userId", "이미 사용 중인 아이디입니다: " + userId);
        }

        boolean isExistUserEmail = userRepository.existsByEmail(email);
        if (isExistUserEmail) {
            throw new DuplicateException("email", "이미 사용 중인 이메일입니다: " + email);
        }

        UserEntity newUser = UserEntity.builder()
                .userId(userId)
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .email(email)
                .phone(phone)
                .role(role)
                .build();

        userRepository.save(newUser);
    }
}
