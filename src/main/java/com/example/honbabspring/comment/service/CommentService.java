package com.example.honbabspring.comment.service;

import com.example.honbabspring.global.snowflake.src.main.java.kuke.board.common.snowflake.Snowflake;
import com.example.honbabspring.comment.dto.request.CommentCreateRequest;
import com.example.honbabspring.comment.dto.response.CommentPageResponseDto;
import com.example.honbabspring.comment.dto.response.CommentResponseDto;
import com.example.honbabspring.comment.entity.Comment;
import com.example.honbabspring.comment.repository.CommentRepository;
import com.example.honbabspring.global.util.PageLimitCalculator;
import com.example.honbabspring.post.entity.Post;
import com.example.honbabspring.post.repository.PostRepository;
import com.example.honbabspring.user.entity.User;
import com.example.honbabspring.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Snowflake snowflake = new Snowflake();

    @Transactional
    public CommentResponseDto create(CommentCreateRequest request) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        User writer = userRepository.findById(request.getWriterId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Comment parent = findParent(request);
        Comment comment = commentRepository.save(
                Comment.create(
                        snowflake.nextId(),
                        request.getContent(),
                        parent == null ? null : parent.getCommentId(),
                        post,
                        writer

                )
        );
        return CommentResponseDto.from(comment);
    }

    private Comment findParent(CommentCreateRequest request) {
        Long parentCommentId = request.getParentCommentId();
        if(parentCommentId == null){
            return null;
        }
        return commentRepository.findById(parentCommentId)
                .filter(not(Comment::getDeleted))
                .filter(Comment::isRoot)
                .orElseThrow();
    }

    public CommentResponseDto read(Long commentId) {
        return CommentResponseDto.from(commentRepository.findById(commentId).orElseThrow());
    }

    @Transactional
    public void delete(Long commentId) {
        commentRepository.findById(commentId)
                .filter(not(Comment::getDeleted))
                .ifPresent(comment -> {
                    if (hasChildren(comment)) {
                        comment.delete();
                    } else {
                        delete(comment);
                    }
                });
    }

    private boolean hasChildren(Comment comment) {
        return commentRepository.countBy(comment.getPost().getPostId(), comment.getCommentId(), 2L) == 2;
    }

    private void delete(Comment comment) {
        commentRepository.delete(comment);
        if (!comment.isRoot()) {
            commentRepository.findById(comment.getParentCommentId())
                    .filter(Comment::getDeleted)
                    .filter(not(this::hasChildren))
                    .ifPresent(this::delete);
        }
    }

    public CommentPageResponseDto readAll(Long postId, Long page, Long pageSize) {
        return CommentPageResponseDto.of(
                commentRepository.findAll(postId, (page - 1) * pageSize, pageSize).stream()
                        .map(CommentResponseDto::from)
                        .toList(),
                commentRepository.count(postId, PageLimitCalculator.calculatePageLimit(page, pageSize, 10L))
        );
    }

    public List<CommentResponseDto> readAll(Long postId, Long lastParentCommentId, Long lastCommentId, Long limit) {
        List<Comment> comments = lastParentCommentId == null || lastCommentId == null ?
                commentRepository.findAllInfiniteScroll(postId, limit) :
                commentRepository.findAllInfiniteScroll(postId, lastParentCommentId, lastCommentId, limit);

        return comments.stream()
                .map(CommentResponseDto::from)
                .toList();
    }
}
