package com.example.honbabspring.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    @Schema(example = "jisssuo222")
    private String userId;

    @Schema(example = "비밀번호 입력하기")
    private String password;
}
