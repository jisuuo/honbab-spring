package com.example.honbabspring.controller;

import com.example.honbabspring.dto.ArticleCreateRequestDto;
import com.example.honbabspring.dto.ArticlePageResponseDto;
import com.example.honbabspring.dto.ArticleResponseDto;
import com.example.honbabspring.dto.ArticleUpdateRequestDto;
import com.example.honbabspring.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Article", description = "게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/articles")
public class ArticleController {
    private final ArticleService articleService;

    @Operation(
            summary = "게시글 단건 조회",
            description = "게시글 ID를 기반으로 게시글 정보를 조회"
    )
    @GetMapping("/{articleId}")
     public ArticleResponseDto read(@PathVariable Long articleId) {
        return articleService.read(articleId);
    }

    @Operation(
            summary = "게시글 모두 조회",
            description = "게시글 ID를 기반으로 모든 게시글 정보를 페이지 형식으로 조회하는 API"
    )
    @GetMapping("/v1/articles")
    public ArticlePageResponseDto readAll(@RequestParam("boardId") Long boardId,
                                          @RequestParam("page") Long page,
                                          @RequestParam("pageSize") Long pageSize) {

        return articleService.readAll(boardId, page, pageSize);

    }

    @Operation(
            summary = "게시글 모두 조회 시 무한 스크롤",
            description = "게시글 ID를 기반으로 모든 게시글 정보를 무한스크롤 형식 조회하는 API"
    )
    @GetMapping("/infinite-scroll")
    public List<ArticleResponseDto> readAllInfiniteScroll(@RequestParam("boardId") Long boardId, @RequestParam("pageSize") Long pageSize, @RequestParam(value = "lastArticleId" ,required = false) Long lastArticleId) {
        return articleService.readAllInfiniteScroll(boardId, pageSize, lastArticleId);
    }

    @Operation(
            summary = "게시글 생성",
            description = "게시글 생성하는 API"
    )
    @PostMapping
    public ArticleResponseDto create(@RequestBody ArticleCreateRequestDto request) {
        return articleService.create(request);
    }

    @Operation(
            summary = "게시글 수정",
            description = "기존 게시글 ID 내용 수정하는 API"
    )
    @PutMapping("/{articleId}")
    public ArticleResponseDto update(@PathVariable Long articleId, @RequestBody ArticleUpdateRequestDto request) {
        return articleService.update(articleId, request);
    }

    @Operation(
            summary = "게시글 삭제",
            description = "기존 게시글 ID 내용 삭제하는 API"
    )
    @DeleteMapping("/{articleId}")
    public void delete(@PathVariable Long articleId) {
        articleService.delete(articleId);
    }

}
