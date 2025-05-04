package com.example.honbabspring.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenDto {

    @Schema(example = "grantType")
    private String grantType;

    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Iuy1nOyngOyImCIsInJvbGUiOiJST0xFX0FETUlOIiwiaWF0IjoxNzQ0Mzg3NjQ3LCJleHAiOjE3NDQzOTEyNDd9.L8twVoZlE3IO6AKA4BYvuP1YiquiyLbBFWjBAwn7AOU")
    private String accessToken;

    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Iuy1nOyngOyImCIsInJvbGUiOiJST0xFX0FETUlOIiwiaWF0IjoxNzQ0Mzg3NjQ3LCJleHAiOjE3NDQzOTEyNDd9.L8twVoZlE3IO6AKA4BYvuP1YiquiyLbBFWjBAwn7AOU")
    private String refreshToken;

    @Schema(example = "토큰만료일")
    private long accessTokenExpiresIn;
}
