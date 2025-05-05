package com.example.honbabspring.comment.entity;

import com.example.honbabspring.post.entity.Post;
import com.example.honbabspring.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "comment")
@Getter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    private Long commentId;

    private String content;

    private Long parentCommentId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    private Boolean deleted;

    private LocalDateTime createdAt;

    public static Comment create(Long commentId, String content, Long parentCommentId, Post post, User user) {
        Comment comment = new Comment();
        comment.commentId = commentId;
        comment.content = content;
        comment.parentCommentId = parentCommentId == null ? commentId : parentCommentId;
        comment.post = post;
        comment.author = user;
        comment.deleted = false;
        comment.createdAt = LocalDateTime.now();
        return comment;
    }


    public static Comment create(Long commentId, String content, Long parentCommentId) {
        Comment comment = new Comment();
        comment.commentId = commentId;
        comment.content = content;
        comment.parentCommentId = (parentCommentId == null) ? commentId : parentCommentId;
        comment.deleted = false;
        comment.createdAt = LocalDateTime.now();
        return comment;
    }

    public boolean isRoot() {
        return parentCommentId.longValue() == commentId;
    }

    public void delete() {
        deleted = true;
    }

}
