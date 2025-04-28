package com.example.honbabspring.Article.api;

import com.example.honbabspring.dto.ArticlePageResponseDto;
import com.example.honbabspring.dto.ArticleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class ArticleAPITest {
    RestClient restClient = RestClient.create("http://localhost:8080");

    @Test
    void createTest() {
        ArticleResponseDto response = create(new ArticleCreateRequestDto(
                "hi", "my content", 1L, 1L
        ));
        System.out.println("response = " + response);
    }

    ArticleResponseDto create(ArticleCreateRequestDto request) {
        return restClient.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponseDto.class);
    }

    @Test
    void readTest() {
        ArticleResponseDto response = read(172177386824990720L);
        System.out.println("response = " + response);
    }

    ArticleResponseDto read(Long articleId) {
        return restClient.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponseDto.class);
    }

    @Test
    void updateTest() {
        update(172171776810893312L);
        ArticleResponseDto response = read(172171776810893312L);
        System.out.println("response = " + response);
    }

    void update(Long articleId) {
        restClient.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(new ArticleUpdateRequestDto("hi 2", "my content 22"))
                .retrieve()
                .body(ArticleResponseDto.class);
    }

    @Test
    void deleteTest() {
        restClient.delete()
                .uri("/v1/articles/{articleId}", 172171776810893312L)
                .retrieve()
                .body(ArticleResponseDto.class);
    }

    @Test
    void readAllTest() {
        ArticlePageResponseDto response = restClient.get()
                .uri("/v1/articles?boardId=1&pageSize=30&page=1")
                .retrieve()
                .body(ArticlePageResponseDto.class);

        System.out.println("response.getArticleCount = " + response.getArticleCount());
    }

    @Test
    void readAllInfiniteScrollTest() {
        List<ArticleResponseDto> articles1 = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponseDto>>() {
                });

        System.out.println("firstPage");
        for (ArticleResponseDto article : articles1) {
            System.out.println("article.getArticleId() = " + article.getArticleId());
        }

        Long lastArticleId = articles1.get(articles1.size() - 1).getArticleId();
        List<ArticleResponseDto> articles2 = restClient.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5&lastArticleId=%s".formatted(lastArticleId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponseDto>>() {
                });
        System.out.println("secondPage");
        for (ArticleResponseDto article : articles2) {
            System.out.println("article.getArticleId() = " + article.getArticleId());
        }

    }


    @Getter
    @AllArgsConstructor
    static class ArticleCreateRequestDto {
        private String title;
        private String content;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    public static class ArticleUpdateRequestDto {
        private String title;
        private String content;
    }


}
