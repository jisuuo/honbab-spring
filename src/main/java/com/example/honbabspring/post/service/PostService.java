package com.example.honbabspring.post.service;

import com.example.honbabspring.global.snowflake.src.main.java.kuke.board.common.snowflake.Snowflake;
import com.example.honbabspring.post.dto.request.PostCreateRequestDto;
import com.example.honbabspring.post.dto.response.PostPageResponseDto;
import com.example.honbabspring.post.dto.response.PostResponseDto;
import com.example.honbabspring.post.dto.request.PostUpdateRequestDto;
import com.example.honbabspring.post.entity.Post;
import com.example.honbabspring.post.repository.PostRepository;
import com.example.honbabspring.global.util.PageLimitCalculator;
import com.example.honbabspring.user.entity.User;
import com.example.honbabspring.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final Snowflake snowflake = new Snowflake();
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto create(PostCreateRequestDto request) {
        User user = userRepository.findByUserId(request.getAuthorId()).orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        Post post = postRepository.save(
                Post.create(snowflake.nextId(), request.getTitle(), request.getContent(), request.getBoardId(), user, request.isPublicStatus(), request.isReplyStatus())
        );

        return PostResponseDto.from(post);
    }

    @Transactional
    public PostResponseDto update(Long postId, PostUpdateRequestDto request) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.update(request.getTitle(), request.getContent());
        return PostResponseDto.from(post);
    }

    public PostResponseDto read(Long postId) {
        return PostResponseDto.from(postRepository.findById(postId).orElseThrow());
    }
    
    @Transactional
    public void delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.updateDeleteStatus(true);
    }

    public PostPageResponseDto readAll(Long boardId, Long page, Long pageSize) {
        return PostPageResponseDto.of(
                postRepository.findAll(boardId, (page - 1) * pageSize, pageSize).stream().map(PostResponseDto::from).toList(),
                postRepository.count(boardId, PageLimitCalculator.calculatePageLimit(page, pageSize, 10L))
        );
    }

    public List<PostResponseDto> readAllInfiniteScroll(Long boardId, Long pageSize, Long lastPostId) {
        List<Post> posts = lastPostId == null ?
                postRepository.findAllInfiniteScroll(boardId, pageSize) :
                postRepository.findAllInfiniteScroll(boardId, pageSize, lastPostId);

        return posts.stream().map(PostResponseDto::from).toList();
    }
}
