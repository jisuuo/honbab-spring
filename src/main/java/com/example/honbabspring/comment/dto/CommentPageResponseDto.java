package com.example.honbabspring.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentPageResponseDto {
    private List<CommentResponseDto> comments;

    @Schema(example = "10")
    private Long commentCount;

    public static CommentPageResponseDto of(List<CommentResponseDto> comments, Long commentCount) {
        CommentPageResponseDto response = new CommentPageResponseDto();
        response.comments = comments;
        response.commentCount = commentCount;
        return response;
    }
}
