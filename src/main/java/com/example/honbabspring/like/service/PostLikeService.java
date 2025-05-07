package com.example.honbabspring.like.service;

import com.example.honbabspring.global.snowflake.src.main.java.kuke.board.common.snowflake.Snowflake;
import com.example.honbabspring.like.dto.reponse.PostLikeResponse;
import com.example.honbabspring.like.entity.PostLike;
import com.example.honbabspring.like.entity.PostLikeCount;
import com.example.honbabspring.like.repository.PostLikeCountRepository;
import com.example.honbabspring.like.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final Snowflake snowflake = new Snowflake();
    private final PostLikeRepository postLikeRepository;
    private final PostLikeCountRepository postLikeCountRepository;

    public PostLikeResponse read(Long articleId, Long userId) {
        return postLikeRepository.findByPostIdAndUserId(articleId, userId)
                .map(PostLikeResponse::from)
                .orElseThrow();
    }

    /**
     * update 구문
     */
    @Transactional
    public void likePessimisticLock1(Long postId, Long userId) {
        postLikeRepository.save(PostLike.create(
                        snowflake.nextId(),
                        postId,
                        userId
                )
        );

        int result = postLikeCountRepository.increase(postId);

        if (result == 0) {
            // 최초 요청 시에는 update 되는 레코드가 없으므로, 1로 초기화
            // 트래픽이 순식간에 몰릴 수 있는 상황에는 유실 도리 수 있어, 게시글 생성 시점에 미리 0으로 초기화 해줄 수 있다.
            postLikeCountRepository.save(
                    PostLikeCount.init(postId, 1L)
            );
        }
    }

    @Transactional
    public void unlikePessimisticLock1(Long postId, Long userId) {
        postLikeRepository.findByPostIdAndUserId(postId, userId)
                .ifPresent(postLike -> {
                            postLikeRepository.delete(postLike);
                            postLikeCountRepository.decrease(postId);
                        }
                );
    }


    /**
     * Select .... for update 구문
     */
    @Transactional
    public void likePessimisticLock2(Long postId, Long userId) {
        postLikeRepository.save(PostLike.create(
                        snowflake.nextId(),
                        postId,
                        userId
                )
        );
        PostLikeCount postLikeCount = postLikeCountRepository.findLockedByPostId(postId).orElseGet(() -> PostLikeCount.init(postId, 0L));
        postLikeCount.increase();
        postLikeCountRepository.save(postLikeCount);
    }

    @Transactional
    public void unlikePessimisticLock2(Long postId, Long userId) {
        postLikeRepository.findByPostIdAndUserId(postId, userId)
                .ifPresent(postLike -> {
                    postLikeRepository.delete(postLike);
                    PostLikeCount postLikeCount = postLikeCountRepository.findLockedByPostId(postId).orElseThrow();
                    postLikeCount.decrease();
                });
    }


    /**
     * 낙관적 락
     */
    @Transactional
    public void likeOptimisticLock(Long postId, Long userId) {
        postLikeRepository.save(PostLike.create(
                        snowflake.nextId(),
                        postId,
                        userId
                )
        );
        PostLikeCount postLikeCount = postLikeCountRepository.findById(postId).orElseGet(() -> PostLikeCount.init(postId, 0L));
        postLikeCount.increase();
        postLikeCountRepository.save(postLikeCount);
    }

    @Transactional
    public void unlikeOptimisticLock(Long postId, Long userId) {
        postLikeRepository.findByPostIdAndUserId(postId, userId)
                .ifPresent(postLike -> {
                    postLikeRepository.delete(postLike);
                    PostLikeCount postLikeCount = postLikeCountRepository.findById(postId).orElseThrow();
                    postLikeCount.decrease();
                });
    }

    public Long count(Long postId) {
        return postLikeCountRepository.findById(postId)
                .map(PostLikeCount::getLikeCount)
                .orElse(0L);
    }

}
