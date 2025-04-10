package com.example.honbabspring.repository;

import com.example.honbabspring.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);

    UserEntity findByUsername(String username);
}
