package com.example.honbabspring.post.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PostPageResponseDto {

    private List<PostResponseDto> posts;

    @Schema(example = "10")
    private Long postCount;

    public static PostPageResponseDto of(List<PostResponseDto> posts, Long postCount) {
        PostPageResponseDto response = new PostPageResponseDto();
        response.posts = posts;
        response.postCount = postCount;
        return response;
    }
}
