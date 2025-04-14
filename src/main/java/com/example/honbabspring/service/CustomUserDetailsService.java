package com.example.honbabspring.service;

import com.example.honbabspring.dto.CustomUserDetails;
import com.example.honbabspring.entity.User;
import com.example.honbabspring.repository.AuthRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepository userRepository;

    public CustomUserDetailsService(AuthRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User userData = userRepository.findByUserId(userId);

        if(userData != null) {
            return new CustomUserDetails(userData);
        }
        return null;
    }
}
