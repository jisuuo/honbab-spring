package com.example.honbabspring.article.dto;

import com.example.honbabspring.article.entity.Article;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleResponseDto {
    private Long articleId;
    private String title;
    private String content;
    private Long boardId;
    private Long writerId;

    public static ArticleResponseDto from(Article article) {
        ArticleResponseDto response = new ArticleResponseDto();
        response.articleId = article.getArticleId();
        response.title = article.getTitle();
        response.content = article.getContent();
        response.boardId = article.getBoardId();
        response.writerId = article.getWriterId();

        return response;
    }
}
