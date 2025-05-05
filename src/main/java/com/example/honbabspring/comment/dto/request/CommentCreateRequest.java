package com.example.honbabspring.comment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommentCreateRequest {

    @Schema(example = "1")
    private Long postId;

    @Schema(example = "맛있나요?")
    private String content;

    @Schema(example = "176280893208199168")
    private Long parentCommentId;

    @Schema(example = "jissuoo22")
    private Long writerId;
}
