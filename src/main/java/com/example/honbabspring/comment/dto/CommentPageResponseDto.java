package com.example.honbabspring.comment.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CommentPageResponseDto {
    private List<CommentResponseDto> comments;
    private Long commentCount;

    public static CommentPageResponseDto of(List<CommentResponseDto> comments, Long commentCount) {
        CommentPageResponseDto response = new CommentPageResponseDto();
        response.comments = comments;
        response.commentCount = commentCount;
        return response;
    }
}
