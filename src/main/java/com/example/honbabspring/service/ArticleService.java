package com.example.honbabspring.service;

import com.example.honbabspring.common.snowflake.src.main.java.kuke.board.common.snowflake.Snowflake;
import com.example.honbabspring.dto.ArticleCreateRequestDto;
import com.example.honbabspring.dto.ArticlePageResponseDto;
import com.example.honbabspring.dto.ArticleResponseDto;
import com.example.honbabspring.dto.ArticleUpdateRequestDto;
import com.example.honbabspring.entity.Article;
import com.example.honbabspring.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final Snowflake snowflake = new Snowflake();
    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponseDto create(ArticleCreateRequestDto request) {
        Article article = articleRepository.save(
                Article.create(snowflake.nextId(), request.getTitle(), request.getContent(), request.getBoardId(), request.getWriterId())
        );

        return ArticleResponseDto.from(article);
    }

    @Transactional
    public ArticleResponseDto update(Long articleId, ArticleUpdateRequestDto request) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.update(request.getTitle(), request.getContent());
        return ArticleResponseDto.from(article);
    }

    public ArticleResponseDto read(Long articleId) {
        return ArticleResponseDto.from(articleRepository.findById(articleId).orElseThrow());
    }
    
    @Transactional
    public void delete(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        articleRepository.delete(article);
    }

    public ArticlePageResponseDto readAll(Long boardId, Long page, Long pageSize) {
        return ArticlePageResponseDto.of(
                articleRepository.findAll(boardId, (page - 1) * pageSize, pageSize).stream().map(ArticleResponseDto::from).toList(),
                articleRepository.count(boardId, PageLimitCalculator.calculatePageLimit(page, pageSize, 10L))
        );
    }

    public List<ArticleResponseDto> readAllInfiniteScroll(Long boardId, Long pageSize, Long lastArticleId) {
        List<Article> articles = lastArticleId == null ?
                articleRepository.findAllInfiniteScroll(boardId, pageSize) :
                articleRepository.findAllInfiniteScroll(boardId, pageSize, lastArticleId);

        return articles.stream().map(ArticleResponseDto::from).toList();
    }
}
