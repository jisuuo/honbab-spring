package com.example.honbabspring.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDto {

    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Iuy1nOyngOyImCIsInJvbGUiOiJST0xFX0FETUlOIiwiaWF0IjoxNzQ0Mzg3NjQ3LCJleHAiOjE3NDQzOTEyNDd9.L8twVoZlE3IO6AKA4BYvuP1YiquiyLbBFWjBAwn7AOU")
    private String accessToken;

    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6Iuy1nOyngOyImCIsInJvbGUiOiJST0xFX0FETUlOIiwiaWF0IjoxNzQ0Mzg3NjQ3LCJleHAiOjE3NDQzOTEyNDd9.L8twVoZlE3IO6AKA4BYvuP1YiquiyLbBFWjBAwn7AOU")
    private String refreshToken;
}
