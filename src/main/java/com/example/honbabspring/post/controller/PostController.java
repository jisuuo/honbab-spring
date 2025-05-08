package com.example.honbabspring.post.controller;

import com.example.honbabspring.post.dto.request.PostCreateRequestDto;
import com.example.honbabspring.post.dto.response.PostPageResponseDto;
import com.example.honbabspring.post.dto.response.PostResponseDto;
import com.example.honbabspring.post.dto.request.PostUpdateRequestDto;
import com.example.honbabspring.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "게시글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostController {
    private final PostService postService;

    @Operation(
            summary = "게시글 단건 조회",
            description = "게시글 ID를 기반으로 게시글 정보를 조회"
    )

    @GetMapping("/{postId}")
     public PostResponseDto read(@PathVariable Long postId) {
        return postService.read(postId);
    }

    @Operation(
            summary = "게시글 모두 조회",
            description = "게시글 ID를 기반으로 모든 게시글 정보를 페이지 형식으로 조회하는 API"
    )
    @GetMapping("/v1/posts")
    public PostPageResponseDto readAll(@RequestParam("boardId") Long boardId,
                                       @RequestParam("page") Long page,
                                       @RequestParam("pageSize") Long pageSize) {

        return postService.readAll(boardId, page, pageSize);

    }

    @Operation(
            summary = "게시글 모두 조회 시 무한 스크롤",
            description = "게시글 ID를 기반으로 모든 게시글 정보를 무한스크롤 형식 조회하는 API"
    )
    @GetMapping("/infinite-scroll")
    public List<PostResponseDto> readAllInfiniteScroll(@RequestParam("boardId") Long boardId, @RequestParam("pageSize") Long pageSize, @RequestParam(value = "lastPostId" ,required = false) Long lastPostId) {
        return postService.readAllInfiniteScroll(boardId, pageSize, lastPostId);
    }

    @Operation(
            summary = "게시글 생성",
            description = "게시글 생성하는 API"
    )
    @PostMapping
    public PostResponseDto create(@RequestBody PostCreateRequestDto request) {
        return postService.create(request);
    }

    @Operation(
            summary = "게시글 수정",
            description = "기존 게시글 ID 내용 수정하는 API"
    )
    @PutMapping("/{postId}")
    public PostResponseDto update(@PathVariable Long postId, @RequestBody PostUpdateRequestDto request) {
        return postService.update(postId, request);
    }

    @Operation(
            summary = "게시글 삭제",
            description = "기존 게시글 ID 내용 삭제하는 API"
    )
    @DeleteMapping("/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }

    @GetMapping("/boards/{boardId}/count")
    public Long count(@PathVariable Long boardId) {
        return postService.count(boardId);
    }

}
