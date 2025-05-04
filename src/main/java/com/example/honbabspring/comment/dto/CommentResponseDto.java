package com.example.honbabspring.comment.dto;

import com.example.honbabspring.comment.entity.Comment;
import com.example.honbabspring.comment.entity.CommentV2;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CommentResponseDto {

//    @Id
    @Schema(example = "1")
    private Long commentId;

    @Schema(example = "맛있나요")
    private String content;

    @Schema(example = "176280893208199168")
    private Long parentCommentId;

    @Schema(example = "1")
    private Long articleId;

    @Schema(example = "jissuo22")
    private Long writerId;

    @Schema(example = "false")
    private boolean deleted;

    @Schema(example = "false")
    private String path;

    @Schema(example = "2025-05-04T07:13:48.852Z")
    private LocalDateTime createdAt;

    public static CommentResponseDto from(Comment comment) {
        CommentResponseDto response = new CommentResponseDto();
        response.commentId = comment.getCommentId();
        response.content = comment.getContent();
        response.parentCommentId = comment.getParentCommentId();
        response.articleId = comment.getArticleId();
        response.writerId = comment.getWriterId();
        response.deleted = comment.getDeleted();
        response.createdAt = comment.getCreatedAt();

        return response;
    }

    
    public static CommentResponseDto from(CommentV2 comment) {
        CommentResponseDto response = new CommentResponseDto();
        response.commentId = comment.getCommentId();
        response.content = comment.getContent();
        response.path = comment.getCommentPath().getPath();
        response.articleId = comment.getArticleId();
        response.writerId = comment.getWriterId();
        response.deleted = comment.getDeleted();
        response.createdAt = comment.getCreatedAt();

        return response;
    }
}
