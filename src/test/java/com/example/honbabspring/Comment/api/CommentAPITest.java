package com.example.honbabspring.Comment.api;

import com.example.honbabspring.dto.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class CommentAPITest {
    RestClient restclient = RestClient.create("http://localhost:8080");

    @Test
    void create() {
        CommentResponse response1 = createComment(new CommentCreateRequest(1L, "my comment1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(1L, "my comment2", response1.getCommentId(), 1L));
        CommentResponse response3 = createComment(new CommentCreateRequest(1L, "my comment3", response1.getCommentId(), 1L));

        System.out.println("commentId=%s".formatted(response1.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response2.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response3.getCommentId()));

    }

    CommentResponse createComment(CommentCreateRequest request) {
        return restclient.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void read() {
        CommentResponse response = restclient.get()
                .uri("/v1/comments/{commentId}", 176219899366682624L)
                .retrieve()
                .body(CommentResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void delete() {

//        commentId=176219899366682624
//            commentId=176219899815473152
//            commentId=176219899857416192

        restclient.delete()
                .uri("/v1/comments/{commentId}", 176219899857416192L)
                .retrieve()
                .body(Void.class);
    }


    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequest {
        private Long articleId;
        private String content;
        private Long parentCommentId;
        private Long writerId;
    }
}
