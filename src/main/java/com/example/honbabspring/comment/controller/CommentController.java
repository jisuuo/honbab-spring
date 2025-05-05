package com.example.honbabspring.comment.controller;

import com.example.honbabspring.comment.dto.request.CommentCreateRequest;
import com.example.honbabspring.comment.dto.response.CommentPageResponseDto;
import com.example.honbabspring.comment.dto.response.CommentResponseDto;
import com.example.honbabspring.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "2 Depth 댓글 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @Operation(
            summary = "댓글 단건 조회",
            description = "댓글 ID를 이용해 2 Depth 해당 댓글의 상세 정보를 조회 API"
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
    public CommentResponseDto create(@RequestBody CommentCreateRequest request) {
        return commentService.create(request);
    }

    @Operation(
            summary = "댓글 삭제",
            description = "댓글 삭제하는 API"
    )
    @DeleteMapping("/v1/comments/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId) {
        commentService.delete(commentId);
    }

    @Operation(
            summary = "댓글 모두 조회",
            description = "페이지형식으로 댓글 모두 조회하는 API"
    )
    @GetMapping()
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
    public List<CommentResponseDto> readAll(
            @RequestParam("postId") Long postId,
            @RequestParam(value = "lastParentCommentId", required = false) Long lastParentCommentId,
            @RequestParam(value = "lastCommentId", required = false) Long lastCommentId,
            @RequestParam("pageSize") Long pageSize
    ) {
        return commentService.readAll(postId, lastParentCommentId, lastCommentId, pageSize);
    }
}
