package com.example.honbabspring.controller;

import com.example.honbabspring.DTO.JoinDTO;
import com.example.honbabspring.service.JoinService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService) {
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String join(JoinDTO joinDTO) {
        joinService.join(joinDTO);
        return "join success";
    }
}
