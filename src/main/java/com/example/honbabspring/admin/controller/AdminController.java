package com.example.honbabspring.admin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin", description = "관리자 페이지 관련 API")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Operation(summary = "관리자 페이지", description = "관리자인 경우 한해 처리하는 API")
    public String adminP() {
        return "Admin Page";
    }
}
