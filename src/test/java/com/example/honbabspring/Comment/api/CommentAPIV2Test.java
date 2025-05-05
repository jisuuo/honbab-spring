package com.example.honbabspring.Comment.api;

import com.example.honbabspring.comment.dto.response.CommentPageResponseDto;
import com.example.honbabspring.comment.dto.response.CommentResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class CommentAPIV2Test {
    RestClient restClient = RestClient.create("http://localhost:8080");

    @Test
    void create() {
        CommentResponseDto response1 = create(new CommentCreateRequestV2(1L, "my comment1", null, 1L));
        CommentResponseDto response2 = create(new CommentCreateRequestV2(1L, "my comment1", response1.getPath(), 1L));
        CommentResponseDto response3 = create(new CommentCreateRequestV2(1L, "my comment1", response2.getPath(), 1L));

        System.out.println("response1.getPath() = " + response1.getPath());
        System.out.println("response1.getCommentId() = " + response1.getCommentId());
        System.out.println("\tresponse2.getPath() = " + response2.getPath());
        System.out.println("\tresponse2.getCommentId() = " + response2.getCommentId());
        System.out.println("\t\tresponse3.getPath() = " + response3.getPath());
        System.out.println("\t\tresponse3.getCommentId() = " + response3.getCommentId());

//        response1.getPath() = 00002
//        response1.getCommentId() = 176280893208199168
//        response2.getPath() = 0000200000
//        response2.getCommentId() = 176280893552132096
//        response3.getPath() = 000020000000000
//        response3.getCommentId() = 176280893615046656

    }

    CommentResponseDto create(CommentCreateRequestV2 request) {
        return restClient.post()
                .uri("/v2/comments")
                .body(request)
                .retrieve()
                .body(CommentResponseDto.class);
    }

    @Test
    void read() {
        CommentResponseDto response = restClient.get().uri("/v2/comments/{commentId}", 176280893208199168L)
                .retrieve()
                .body(CommentResponseDto.class);

        System.out.println("response = " + response);

    }

    @Test
    void delete() {
        restClient.delete()
                .uri("/v2/comments/{commentId}", 176280893208199168L)
                .retrieve()
                .body(Void.class);
    }

    @Test
    void readAll() {
        CommentPageResponseDto response = restClient.get()
                .uri("/v2/comments?postId=1&pageSize=10&page=1")
                .retrieve()
                .body(CommentPageResponseDto.class);

        System.out.println("response.getCommentCount() = " + response.getCommentCount());
        for (CommentResponseDto comment : response.getComments()) {
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }
    }

    @Test
    void readAllInfiniteScroll() {
        List<CommentResponseDto> responses1 = restClient.get()
                .uri("/v2/comments/infinite-scroll?postId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponseDto>>() {
                });

        System.out.println("firstPage");
        for (CommentResponseDto response : responses1) {
            System.out.println("response.getCommentId() = " + response.getCommentId());
        }

        CommentResponseDto last = responses1.get(responses1.size() - 1);

        String lastPath = last.getPath();
        List<CommentResponseDto> responses2 = restClient.get()
                .uri("/v2/comments/infinite-scroll?postId=1&pageSize=5&lastPath=%s".formatted(lastPath))
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponseDto>>() {
                });

        System.out.println("secondPage");
        for (CommentResponseDto response : responses2) {
            System.out.println("response.getCommentId() = " + response.getCommentId());
        }
    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequestV2 {
        private Long postIdÏ;
        private String content;
        private String parentPath;
        private Long writerId;
    }


}
