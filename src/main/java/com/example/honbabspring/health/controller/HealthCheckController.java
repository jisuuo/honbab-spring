package com.example.honbabspring.health.controller;

import com.example.honbabspring.health.dto.HealthCheckResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Tag(name = "healthCheck", description = "서버 상태 체크 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HealthCheckController {

    private final Environment environment;

    @Tag(name = "healthCheck")
    @Operation(summary = "서버 Health Check API", description = "현재 서버가 정상적으로 기동되는 상태인지 체크하는 API")
    @GetMapping("/health")
    private ResponseEntity<HealthCheckResponseDto> healthCheck() {
        HealthCheckResponseDto healthCheckResponseDto = HealthCheckResponseDto.builder().health("ok").activeProfiles(Arrays.asList(environment.getActiveProfiles())).build();
        return ResponseEntity.ok(healthCheckResponseDto);
    }

}
