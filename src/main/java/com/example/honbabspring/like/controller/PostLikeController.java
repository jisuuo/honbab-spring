package com.example.honbabspring.like.controller;

import com.example.honbabspring.like.dto.reponse.PostLikeResponse;
import com.example.honbabspring.like.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/post-likes")
public class PostLikeController {
    private final PostLikeService postLikeService;

    @GetMapping("/post/{postId}/users/{userId}")
    public PostLikeResponse read(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        return postLikeService.read(postId, userId);
    }

    @GetMapping("/post/{postId}/count")
    public Long count(
            @PathVariable Long postId
    ) {
        return postLikeService.count(postId);
    }

    @PostMapping("/post/{postId}/users/{userId}/pessimistic-lock-1")
    public void likePessimisticLock1(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        postLikeService.likePessimisticLock1(postId, userId);
    }


    @DeleteMapping("/post/{postId}/users/{userId}/pessimistic-lock-1")
    public void unlikePessimisticLock1(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        postLikeService.unlikePessimisticLock1(postId, userId);
    }


    @PostMapping("/post/{postId}/users/{userId}/pessimistic-lock-2")
    public void likePessimisticLock2(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        postLikeService.likePessimisticLock2(postId, userId);
    }


    @DeleteMapping("/post/{postId}/users/{userId}/pessimistic-lock-2")
    public void unlikePessimisticLock2(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        postLikeService.unlikePessimisticLock2(postId, userId);
    }

    @PostMapping("/post/{postId}/users/{userId}/optimistic-lock")
    public void likeOptimisticLock(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        postLikeService.likeOptimisticLock(postId, userId);
    }


    @DeleteMapping("/post/{postId}/users/{userId}/optimistic-lock")
    public void unlikeOptimisticLock(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        postLikeService.unlikeOptimisticLock(postId, userId);
    }
}
