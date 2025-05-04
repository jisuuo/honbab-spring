package com.example.honbabspring.auth.dto;

import com.example.honbabspring.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    @Schema(example = "jisssuo222")
    private String userId;

    @Schema(example = "wltn203@naver.com")
    private String email;

    public static UserResponseDto of(User user) {
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .build();
    }
}