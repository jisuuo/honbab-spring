package com.example.honbabspring.like.api;

import com.example.honbabspring.like.dto.reponse.PostLikeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class LikeAPITest {
    RestClient restClient = RestClient.create("http://localhost:8080/dev");

    @Test
    void likeAndUnlikeTest() {
        Long postId = 9999L;

        like(postId, 1L);
        like(postId, 2L);
        like(postId, 3L);

        PostLikeResponse response1 = read(postId, 1L);
        PostLikeResponse response2 = read(postId, 2L);
        PostLikeResponse response3 = read(postId, 3L);
        System.out.println("response1 = " + response1);
        System.out.println("response2 = " + response2);
        System.out.println("response3 = " + response3);

        unlike(postId, 1L);
        unlike(postId, 2L);
        unlike(postId, 3L);

    }

    void like(Long postId, Long userId) {
        restClient.post()
                .uri("/v1/post-likes/post/{postId}/users/{userId}", postId, userId)
                .retrieve()
                .body(Void.class);
    }

    void unlike(Long postId, Long userId) {
        restClient.delete()
                .uri("/v1/post-likes/post/{postId}/users/{userId}", postId, userId)
                .retrieve()
                .body(Void.class);
    }

    PostLikeResponse read(Long postId, Long userId) {
        return restClient.get()
                .uri("/v1/post-likes/post/{postId}/users/{userId}", postId, userId)
                .retrieve()
                .body(PostLikeResponse.class);
    }
}
