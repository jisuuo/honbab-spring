package com.example.honbabspring.repository;

import com.example.honbabspring.post.repository.PostRepository;
import com.example.honbabspring.post.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Test
    void findAllTest() {
        List<Post> posts = postRepository.findAll(1L, 1499970L, 30L);
        log.info("posts.size = {}", posts.size());
        for (Post post : posts) {
            log.info("post = {}", post);
        }
    }

    @Test
    void countTest() {
        Long count = postRepository.count(1L, 10000L);
        log.info("count = {}", count);

    }

    @Test
    void findInfiniteScrollTest() {
        List<Post> posts = postRepository.findAllInfiniteScroll(1L, 30L);
        for (Post post : posts) {
            log.info("postId = {}", post.getPostId());
        }

        Long lastPostId = posts.get(posts.size() - 1).getPostId();
        List<Post> posts2 = postRepository.findAllInfiniteScroll(1L, 30L, lastPostId);
        for (Post post : posts2) {
            log.info("postId = {}", post.getPostId());
        }
    }

}