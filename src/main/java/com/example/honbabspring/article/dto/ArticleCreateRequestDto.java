package com.example.honbabspring.article.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleCreateRequestDto {

    @Schema(example = "맛집 추천!")
    private String title;

    @Schema(example = "여기 맛있어요")
    private String content;

    @Schema(example = "jisssuo222")
    private Long writerId;

    @Schema(example = "1")
    private Long boardId;
}
