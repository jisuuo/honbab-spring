package com.example.honbabspring.like.service;

import com.example.honbabspring.global.snowflake.src.main.java.kuke.board.common.snowflake.Snowflake;
import com.example.honbabspring.like.dto.reponse.PostLikeResponse;
import com.example.honbabspring.like.entity.PostLike;
import com.example.honbabspring.like.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final Snowflake snowflake = new Snowflake();
    private final PostLikeRepository postLikeRepository;

    public PostLikeResponse read(Long articleId, Long userId) {
        return postLikeRepository.findByPostIdAndUserId(articleId, userId)
                .map(PostLikeResponse::from)
                .orElseThrow();
    }

    @Transactional
    public void like(Long postId, Long userId) {
        postLikeRepository.save(PostLike.create(
                        snowflake.nextId(),
                        postId,
                        userId
                )
        );
    }

    @Transactional
    public void unlike(Long postId, Long userId) {
        postLikeRepository.findByPostIdAndUserId(postId, userId)
                .ifPresent(postLikeRepository::delete);
    }


}
