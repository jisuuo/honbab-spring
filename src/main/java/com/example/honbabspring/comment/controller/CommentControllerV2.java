package com.example.honbabspring.comment.controller;

import com.example.honbabspring.comment.dto.request.CommentCreateRequestV2;
import com.example.honbabspring.comment.dto.response.CommentPageResponseDto;
import com.example.honbabspring.comment.dto.response.CommentResponseDto;
import com.example.honbabspring.comment.service.CommentServiceV2;
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
    public CommentResponseDto read(@PathVariable("commentId") Long commentId) {
        return commentService.read(commentId);
    }


    @Operation(
            summary = "댓글 생성",
            description = "댓글 생성하는 API"
    )
    @PostMapping
    public CommentResponseDto create(@RequestBody CommentCreateRequestV2 request) {
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
    public CommentPageResponseDto readAll(
            @RequestParam("postId") Long postId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ) {
        return commentService.readAll(postId, page, pageSize);
    }

    @Operation(
            summary = "댓글 모두 조회",
            description = "무한스크롤형식으로 댓글 모두 조회하는 API"
    )
    @GetMapping("/infinite-scroll")
    public List<CommentResponseDto> readAllInfiniteScroll(
            @RequestParam("postId") Long postId,
            @RequestParam(value = "lastPath", required = false) String lastPath,
            @RequestParam("pageSize") Long pageSize
    ) {
        return commentService.readAllInfiniteScroll(postId, lastPath, pageSize);
    }

//    @GetMapping("/v2/comments/posts/{postId}/count")
//    public Long count(
//            @PathVariable("postId") Long postId
//    ) {
//        return commentService.count(postId);
//    }

}
