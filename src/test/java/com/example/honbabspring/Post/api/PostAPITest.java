package com.example.honbabspring.Post.api;

import com.example.honbabspring.post.dto.response.PostPageResponseDto;
import com.example.honbabspring.post.dto.response.PostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class PostAPITest {
    RestClient restClient = RestClient.create("http://localhost:8080");

    @Test
    void createTest() {
        PostResponseDto response = create(new PostCreateRequestDto(
                "hi", "my content", 1L, 1L
        ));
        System.out.println("response = " + response);
    }

    PostResponseDto create(PostCreateRequestDto request) {
        return restClient.post()
                .uri("/v1/posts")
                .body(request)
                .retrieve()
                .body(PostResponseDto.class);
    }

    @Test
    void readTest() {
        PostResponseDto response = read(172177386824990720L);
        System.out.println("response = " + response);
    }

    PostResponseDto read(Long postId) {
        return restClient.get()
                .uri("/v1/posts/{postId}", postId)
                .retrieve()
                .body(PostResponseDto.class);
    }

    @Test
    void updateTest() {
        update(172171776810893312L);
        PostResponseDto response = read(172171776810893312L);
        System.out.println("response = " + response);
    }

    void update(Long postId) {
        restClient.put()
                .uri("/v1/posts/{postId}", postId)
                .body(new PostUpdateRequestDto("hi 2", "my content 22"))
                .retrieve()
                .body(PostResponseDto.class);
    }

    @Test
    void deleteTest() {
        restClient.delete()
                .uri("/v1/posts/{postId}", 172171776810893312L)
                .retrieve()
                .body(PostResponseDto.class);
    }

    @Test
    void readAllTest() {
        PostPageResponseDto response = restClient.get()
                .uri("/v1/posts?boardId=1&pageSize=30&page=1")
                .retrieve()
                .body(PostPageResponseDto.class);

        System.out.println("response.getPostCount = " + response.getPostCount());
    }

    @Test
    void readAllInfiniteScrollTest() {
        List<PostResponseDto> post1 = restClient.get()
                .uri("/v1/posts/infinite-scroll?boardId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<PostResponseDto>>() {
                });

        System.out.println("firstPage");
        for (PostResponseDto post : post1) {
            System.out.println("post.getPostId() = " + post.getPostId());
        }

        Long lastPostId = post1.get(post1.size() - 1).getPostId();
        List<PostResponseDto> posts2 = restClient.get()
                .uri("/v1/posts/infinite-scroll?boardId=1&pageSize=5&lastPostId=%s".formatted(lastPostId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<PostResponseDto>>() {
                });
        System.out.println("secondPage");
        for (PostResponseDto post : posts2) {
            System.out.println("post.getPostId() = " + post.getPostId());
        }

    }


    @Getter
    @AllArgsConstructor
    static class PostCreateRequestDto {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    public static class PostUpdateRequestDto {
        private String title;
        private String content;
    }


}
