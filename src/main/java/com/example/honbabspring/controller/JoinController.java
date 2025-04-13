package com.example.honbabspring.controller;

import com.example.honbabspring.dto.JoinRequestDto;
import com.example.honbabspring.service.JoinService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String join(@Valid JoinRequestDto joinDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage(); // 첫 번째 에러 메시지 반환
        }
        joinService.join(joinDTO);
        return "회원가입 성공!";
    }
}
