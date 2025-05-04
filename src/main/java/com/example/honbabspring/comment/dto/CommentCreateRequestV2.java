package com.example.honbabspring.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommentCreateRequestV2 {

    @Schema(example = "1")
    private Long articleId;

    @Schema(example = "맛있나요?")
    private String content;

    @Schema(example = "00002")
    private String parentPath;

    @Schema(example = "jissuoo22")
    private Long writerId;
}
