package com.example.honbabspring.like.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "post_like")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {

    @Id
    private Long postLikeId;
    private Long postId;
    private Long userId;
    private LocalDateTime createdAt;

    public static PostLike create(Long postLikeId, Long postId, Long userId) {
        PostLike postLike = new PostLike();
        postLike.postLikeId = postLikeId;
        postLike.postId = postId;
        postLike.userId = userId;
        postLike.createdAt = LocalDateTime.now();
        return postLike;
    }

}
