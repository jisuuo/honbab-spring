package com.example.honbabspring.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ArticlePageResponseDto {

    private List<ArticleResponseDto> articles;

    @Schema(example = "10")
    private Long articleCount;

    public static ArticlePageResponseDto of(List<ArticleResponseDto> articles, Long articleCount) {
        ArticlePageResponseDto response = new ArticlePageResponseDto();
        response.articles = articles;
        response.articleCount = articleCount;
        return response;
    }
}
