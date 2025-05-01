package com.example.honbabspring.dto;

import com.example.honbabspring.entity.Comment;
import com.example.honbabspring.entity.CommentV2;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class CommentResponse {
    @Id
    private Long commentId;
    private String content;
    private Long parentCommentId;
    private Long articleId;
    private Long writerId;
    private boolean deleted;
    private String path;
    private LocalDateTime createdAt;

    public static CommentResponse from(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.commentId = comment.getCommentId();
        response.content = comment.getContent();
        response.parentCommentId = comment.getParentCommentId();
        response.articleId = comment.getArticleId();
        response.writerId = comment.getWriterId();
        response.deleted = comment.getDeleted();
        response.createdAt = comment.getCreatedAt();

        return response;
    }

    
    public static CommentResponse from(CommentV2 comment) {
        CommentResponse response = new CommentResponse();
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
