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

    @PostMapping("/post/{postId}/users/{userId}")
    public void like(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        postLikeService.like(postId, userId);
    }


    @DeleteMapping("/post/{postId}/users/{userId}")
    public void unlike(
            @PathVariable Long postId,
            @PathVariable Long userId
    ) {
        postLikeService.unlike(postId, userId);
    }
}
