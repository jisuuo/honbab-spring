package com.example.honbabspring.post.entity;

import com.example.honbabspring.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "post")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    private Long postId;

    private String title;

    private String content;

    private Long boardId;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "author_id")
    private User author;

    private String writerId;

    private boolean publicStatus;

    private boolean replyStatus;

    private boolean deleteStatus;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;

    public static Post create(Long postId, String title, String content, Long boardId, User user, boolean publicStatus, boolean replyStatus) {
        Post post = new Post();
        post.postId = postId;
        post.title = title;
        post.content = content;
        post.boardId = boardId;
        post.author = user;
        post.writerId = user.getUserId();
        post.publicStatus = publicStatus;
        post.replyStatus = replyStatus;
        post.createdAt = LocalDateTime.now();
        post.modifiedAt = post.createdAt;
        return post;
    }

    public static Post create(Long postId, String title, String content, Long boardId) {
        Post post = new Post();
        post.postId = postId;
        post.title = title;
        post.content = content;
        post.boardId = boardId;
        post.createdAt = LocalDateTime.now();
        post.modifiedAt = post.createdAt;
        return post;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        modifiedAt = LocalDateTime.now();
    }

    public void updateDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
        this.deletedAt = LocalDateTime.now();
    }
}
