package com.example.honbabspring.controller;

import com.example.honbabspring.dto.*;
import com.example.honbabspring.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "회원 가입",
            description = "회원 가입하는 API"
    )
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@Valid SignRequestDto joinRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            return ResponseEntity.badRequest().body(new UserResponseDto(null, errorMessage));
        }
        return ResponseEntity.ok(authService.signup(joinRequestDto));
    }

    @Operation(
            summary = "로그인",
            description = "회원 가입된 유저 로그인하는 API"
    )
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(LoginRequestDto  loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @Operation(
            summary = "토큰 재발급",
            description = "액세스 토큰 및 리프레시 토큰 재발급 하는 API"
    )
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}