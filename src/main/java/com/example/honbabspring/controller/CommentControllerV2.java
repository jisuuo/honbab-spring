package com.example.honbabspring.controller;

import com.example.honbabspring.dto.CommentCreateRequestV2;
import com.example.honbabspring.dto.CommentPageResponse;
import com.example.honbabspring.dto.CommentResponse;
import com.example.honbabspring.service.CommentServiceV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "무한 Depth 댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/comments")
public class CommentControllerV2 {
    private final CommentServiceV2 commentService;

    @Operation(
            summary = "댓글 단건 조회",
            description = "댓글 ID를 이용해 해당 댓글의 상세 정보를 조회 API"
    )
    @GetMapping("/{commentId}")
    public CommentResponse read(@PathVariable("commentId") Long commentId) {
        return commentService.read(commentId);
    }


    @Operation(
            summary = "댓글 생성",
            description = "댓글 생성하는 API"
    )
    @PostMapping
    public CommentResponse create(@RequestBody CommentCreateRequestV2 request) {
        return commentService.create(request);
    }

    @Operation(
            summary = "댓글 삭제",
            description = "댓글 삭제하는 API"
    )
    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
    }

    @Operation(
            summary = "댓글 모두 조회",
            description = "페이지형식으로 댓글 모두 조회하는 API"
    )
    @GetMapping
    public CommentPageResponse readAll(
            @RequestParam("articleId") Long articleId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ) {
        return commentService.readAll(articleId, page, pageSize);
    }

    @Operation(
            summary = "댓글 모두 조회",
            description = "무한스크롤형식으로 댓글 모두 조회하는 API"
    )
    @GetMapping("/infinite-scroll")
    public List<CommentResponse> readAllInfiniteScroll(
            @RequestParam("articleId") Long articleId,
            @RequestParam(value = "lastPath", required = false) String lastPath,
            @RequestParam("pageSize") Long pageSize
    ) {
        return commentService.readAllInfiniteScroll(articleId, lastPath, pageSize);
    }

//    @GetMapping("/v2/comments/articles/{articleId}/count")
//    public Long count(
//            @PathVariable("articleId") Long articleId
//    ) {
//        return commentService.count(articleId);
//    }

}
