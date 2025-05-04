package com.example.honbabspring.health.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @Builder
public class HealthCheckResponseDto {

    @Schema(description = "서버 health 상태", example = "ok", requiredMode = Schema.RequiredMode.REQUIRED)
    private String health;

    @Schema(description = "현재 실행 중인 profile", example = "[dev]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> activeProfiles;
}
