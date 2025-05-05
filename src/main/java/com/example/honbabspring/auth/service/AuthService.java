package com.example.honbabspring.auth.service;

import com.example.honbabspring.auth.dto.*;
import com.example.honbabspring.auth.dto.request.LoginRequestDto;
import com.example.honbabspring.auth.dto.request.SignRequestDto;
import com.example.honbabspring.auth.dto.request.TokenRequestDto;
import com.example.honbabspring.auth.dto.response.UserResponseDto;
import com.example.honbabspring.user.entity.User;
import com.example.honbabspring.auth.jwt.TokenProvider;
import com.example.honbabspring.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    private final AuthRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    String refreshToken;

    @Transactional
    public UserResponseDto signup(SignRequestDto signRequest) {
        if (userRepository.existsByUserId(signRequest.getUserId())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        User user = signRequest.toEntity(passwordEncoder.encode(signRequest.getPassword()));
        return UserResponseDto.of(userRepository.save(user));
    }

    public TokenDto login(LoginRequestDto loginRequestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getUserId(), loginRequestDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        if(refreshTokenService.getRefreshToken(loginRequestDto.getUserId()) != null) {
            refreshTokenService.updateRefreshToken(loginRequestDto.getUserId(), tokenDto.getRefreshToken());
        } else {
            refreshTokenService.saveRefreshToken(loginRequestDto.getUserId(), tokenDto.getRefreshToken());
        }

        return tokenDto;

    }

    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        if(!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("RefreshToken 유효하지 않습니다!");
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        refreshToken = refreshTokenService.getRefreshToken(authentication.getName());

        if(refreshToken == null) {
            throw new RuntimeException("로그아웃된 사용자입니다");
        }

        if(!refreshToken.equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저정보가 일치하지 않습니다");
        }

        TokenDto tokenDto = null;

        if(tokenProvider.refreshTokenPeriodCheck(refreshToken)) {
            tokenDto = tokenProvider.generateTokenDto(authentication);

            refreshTokenService.updateRefreshToken(authentication.getName(), tokenDto.getRefreshToken());
        } else {
            tokenDto = tokenProvider.createAccessToken(authentication);
        }
        return tokenDto;
    }
}
