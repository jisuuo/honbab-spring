package com.example.honbabspring.service;

import com.example.honbabspring.entity.User;
import com.example.honbabspring.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //회원 전체 조회
    public List<User> findMembers() {
        return userRepository.findAll();
    }

    public User findById(Long userId) {
        return userRepository.findOne(userId);
    }




}
