package com.example.honbabspring.controller;

import com.example.honbabspring.dto.ArticleCreateRequestDto;
import com.example.honbabspring.dto.ArticlePageResponseDto;
import com.example.honbabspring.dto.ArticleResponseDto;
import com.example.honbabspring.dto.ArticleUpdateRequestDto;
import com.example.honbabspring.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/articles")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/{articleId}")
     public ArticleResponseDto read(@PathVariable Long articleId) {
        return articleService.read(articleId);
    }

    @GetMapping("/v1/articles")
    public ArticlePageResponseDto readAll(@RequestParam("boardId") Long boardId,
                                          @RequestParam("page") Long page,
                                          @RequestParam("pageSize") Long pageSize) {

        return articleService.readAll(boardId, page, pageSize);

    }

    @PostMapping
    public ArticleResponseDto create(@RequestBody ArticleCreateRequestDto request) {
        return articleService.create(request);
    }

    @PutMapping("/{articleId}")
    public ArticleResponseDto update(@PathVariable Long articleId, @RequestBody ArticleUpdateRequestDto request) {
        return articleService.update(articleId, request);
    }

    @DeleteMapping("/{articleId}")
    public void delete(@PathVariable Long articleId) {
        articleService.delete(articleId);
    }

}
