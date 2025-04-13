package com.example.honbabspring.service.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RefreshTokenService {

    private RedisTemplate<String, String> redisTemplate;
    long expiredTime = 7 * 24 * 60 * 60 * 1000;

    public RefreshTokenService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveRefreshToken(String userId, String refreshToken) {
        redisTemplate.opsForValue().set(userId, refreshToken, expiredTime, TimeUnit.MILLISECONDS);
    }

    public String getRefreshToken(String userId) {
        return redisTemplate.opsForValue().get(userId);
    }

    public void deleteRefreshToken(String userId) {
        redisTemplate.delete(userId);
    }

    public void updateRefreshToken(String userId, String newRefreshToken) {
        redisTemplate.opsForValue().set(
                userId,
                newRefreshToken,
                expiredTime,
                TimeUnit.MILLISECONDS
        );

    }
}
