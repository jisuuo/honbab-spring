package com.example.honbabspring.like.repository;

import com.example.honbabspring.like.entity.PostLikeCount;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeCountRepository  extends JpaRepository<PostLikeCount, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<PostLikeCount> findLockedByPostId(Long postId);

    @Query(
            value = "update post_like_count set like_count = like_count + 1 where post_id = :postId",
            nativeQuery = true
    )
    @Modifying
    int increase(@Param("postId") Long postId);


    @Query(
            value = "update post_like_count set like_count = like_count - 1 where post_id = postId",
            nativeQuery = true
    )
    @Modifying
    int decrease(@Param("postId") Long postId);
}
