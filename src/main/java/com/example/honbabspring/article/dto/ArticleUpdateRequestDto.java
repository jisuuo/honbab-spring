package com.example.honbabspring.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleUpdateRequestDto {

    @Schema(example = "맛집 추천!")
    private String title;

    @Schema(example = "맛집 추천해요!")
    private String content;
}
