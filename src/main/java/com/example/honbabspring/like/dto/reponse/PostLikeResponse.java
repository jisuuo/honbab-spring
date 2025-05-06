package com.example.honbabspring.like.dto.reponse;

import com.example.honbabspring.like.entity.PostLike;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class PostLikeResponse {

    private Long postLikeId;
    private Long postId;
    private Long userId;
    private LocalDateTime createdAt;

    public static PostLikeResponse from(PostLike postLike) {
        PostLikeResponse response = new PostLikeResponse();
        response.postLikeId = postLike.getPostLikeId();
        response.postId = postLike.getPostId();
        response.userId = postLike.getUserId();
        response.createdAt = postLike.getCreatedAt();
        return response;
    }
}
