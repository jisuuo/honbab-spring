package com.example.honbabspring.Comment.api;

import com.example.honbabspring.comment.dto.response.CommentPageResponseDto;
import com.example.honbabspring.comment.dto.response.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class CommentAPITest {
    RestClient restClient = RestClient.create("http://localhost:8080");

    @Test
    void create() {
        CommentResponseDto response1 = createComment(new CommentCreateRequest(1L, "my comment1", null, 1L));
        CommentResponseDto response2 = createComment(new CommentCreateRequest(1L, "my comment2", response1.getCommentId(), 1L));
        CommentResponseDto response3 = createComment(new CommentCreateRequest(1L, "my comment3", response1.getCommentId(), 1L));

        System.out.println("commentId=%s".formatted(response1.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response2.getCommentId()));
        System.out.println("\tcommentId=%s".formatted(response3.getCommentId()));

    }

    CommentResponseDto createComment(CommentCreateRequest request) {
        return restClient.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponseDto.class);
    }

    @Test
    void read() {
        CommentResponseDto response = restClient.get()
                .uri("/v1/comments/{commentId}", 176219899366682624L)
                .retrieve()
                .body(CommentResponseDto.class);

        System.out.println("response = " + response);
    }

    @Test
    void delete() {

//        commentId=176219899366682624
//            commentId=176219899815473152
//            commentId=176219899857416192

        restClient.delete()
                .uri("/v1/comments/{commentId}", 176219899857416192L)
                .retrieve()
                .body(Void.class);
    }

    @Test
    void readAll() {
        CommentPageResponseDto response = restClient.get()
                .uri("/v1/comments?postId=1&page=1&pageSize=10")
                .retrieve()
                .body(CommentPageResponseDto.class);

        System.out.println("response.getCommentCount() = " + response.getCommentCount());
        for (CommentResponseDto comment : response.getComments()) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }

        /**
         * 1번 페이지 수행 결과
         * response.getCommentCount() = 101
         * comment.getCommentId() = 176227164551950336
         * 	comment.getCommentId() = 176227164589699072
         * comment.getCommentId() = 176227164556144640
         * 	comment.getCommentId() = 176227164589699078
         * comment.getCommentId() = 176227164556144641
         * 	comment.getCommentId() = 176227164593893376
         * comment.getCommentId() = 176227164556144642
         * 	comment.getCommentId() = 176227164589699074
         * comment.getCommentId() = 176227164556144643
         * 	comment.getCommentId() = 176227164589699073*/

    }

    @Test
    void readAllInfiniteScroll() {
        List<CommentResponseDto> responses1 = restClient.get()
                .uri("/v1/comments/infinite-scroll?postId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponseDto>>() {
                });

        System.out.println("firstPage");
        for (CommentResponseDto comment : responses1) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }

        CommentResponseDto last = responses1.get(responses1.size() - 1);
        Long lastParentCommentId = last.getParentCommentId();
        Long lastCommentId = last.getCommentId();

        List<CommentResponseDto> responses2 = restClient.get()
                .uri("/v1/comments/infinite-scroll?postId=1&pageSize=5&lastParentCommentId=%s&lastCommentId=%s"
                        .formatted(lastParentCommentId, lastCommentId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponseDto>>() {
                });

        System.out.println("secondPage");
        for (CommentResponseDto comment : responses2) {
            if (!comment.getCommentId().equals(comment.getParentCommentId())) {
                System.out.print("\t");
            }
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }
    }


    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequest {
        private Long postId;
        private String content;
        private Long parentCommentId;
        private Long writerId;
    }
}
