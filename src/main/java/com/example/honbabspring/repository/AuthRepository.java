package com.example.honbabspring.repository;

import com.example.honbabspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Long> {

    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);

    User findByUsername(String username);
    User findByUserId(String userId);


}
