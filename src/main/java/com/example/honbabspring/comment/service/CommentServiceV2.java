package com.example.honbabspring.comment.service;

import com.example.honbabspring.common.snowflake.src.main.java.kuke.board.common.snowflake.Snowflake;
import com.example.honbabspring.comment.dto.CommentCreateRequestV2;
import com.example.honbabspring.comment.dto.CommentPageResponseDto;
import com.example.honbabspring.comment.dto.CommentResponseDto;
import com.example.honbabspring.comment.entity.CommentPath;
import com.example.honbabspring.comment.entity.CommentV2;
import com.example.honbabspring.comment.repository.CommentRepositoryV2;
import com.example.honbabspring.common.util.PageLimitCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
public class CommentServiceV2 {
    private final Snowflake snowflake = new Snowflake();
    private final CommentRepositoryV2 commentRepository;

    @Transactional
    public CommentResponseDto create(CommentCreateRequestV2 request) {
        CommentV2 parent = findParent(request);
        CommentPath parentCommentPath = parent == null ? CommentPath.create("") : parent.getCommentPath();
        CommentV2 comment = commentRepository.save(
                CommentV2.create(
                        snowflake.nextId(),
                        request.getContent(),
                        request.getArticleId(),
                        request.getWriterId(),
                        parentCommentPath.createChildCommentPath(
                                commentRepository.findDescendantsTopPath(request.getArticleId(), parentCommentPath.getPath())
                                        .orElse(null)
                        )
                )
        );

        return CommentResponseDto.from(comment);
    }

    private CommentV2 findParent(CommentCreateRequestV2 request) {
        String parentPath = request.getParentPath();
        if(parentPath == null) {
            return null;
        }

        return commentRepository.findByPath(parentPath).filter(not(CommentV2::getDeleted)).orElseThrow();

    }
    @Transactional
    public void delete(Long commentId) {
        commentRepository.findById(commentId)
                .filter(not(CommentV2::getDeleted))
                .ifPresent(comment -> {
                    if(hasChildren(comment)) {
                        comment.delete();
                    } else {
                        delete(comment);
                    }

                });
    }

    private boolean hasChildren(CommentV2 comment) {
        return commentRepository.findDescendantsTopPath(
                comment.getArticleId(),
                comment.getCommentPath().getPath()
        ).isPresent();
    }

    private void delete(CommentV2 comment) {
        commentRepository.delete(comment);
        if (!comment.isRoot()) {
            commentRepository.findByPath(comment.getCommentPath().getParentPath())
                    .filter(CommentV2::getDeleted)
                    .filter(not(this::hasChildren))
                    .ifPresent(this::delete);
        }
    }

    public CommentResponseDto read(Long commentId) {
        return CommentResponseDto.from(
                commentRepository.findById(commentId).orElseThrow()
        );
    }

    public CommentPageResponseDto readAll(Long articleId, Long page, Long pageSize) {
        return CommentPageResponseDto.of(
                commentRepository.findAll(articleId, (page - 1) * pageSize, pageSize).stream()
                        .map(CommentResponseDto::from)
                        .toList(),
                commentRepository.count(articleId, PageLimitCalculator.calculatePageLimit(page, pageSize, 10L))
        );
    }

    public List<CommentResponseDto> readAllInfiniteScroll(Long articleId, String lastPath, Long pageSize) {
        List<CommentV2> comments = lastPath == null ?
                commentRepository.findAllInfiniteScroll(articleId, pageSize) :
                commentRepository.findAllInfiniteScroll(articleId, lastPath, pageSize);

        return comments.stream()
                .map(CommentResponseDto::from)
                .toList();
    }

}
