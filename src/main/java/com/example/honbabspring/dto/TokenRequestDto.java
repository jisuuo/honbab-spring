package com.example.honbabspring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;
}
