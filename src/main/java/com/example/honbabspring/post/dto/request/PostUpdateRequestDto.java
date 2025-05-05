package com.example.honbabspring.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostUpdateRequestDto {

    @Schema(example = "맛집 추천!")
    private String title;

    @Schema(example = "맛집 추천해요!")
    private String content;
}
