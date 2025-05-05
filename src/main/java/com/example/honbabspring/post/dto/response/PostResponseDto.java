package com.example.honbabspring.post.dto.response;

import com.example.honbabspring.post.entity.Post;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private Long boardId;
    private String authorId;

    public static PostResponseDto from(Post post) {
        PostResponseDto response = new PostResponseDto();
        response.postId = post.getPostId();
        response.title = post.getTitle();
        response.content = post.getContent();
        response.boardId = post.getBoardId();
        response.authorId = post.getAuthor().getUserId();

        return response;
    }
}
