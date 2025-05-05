package com.example.honbabspring.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostCreateRequestDto {

    @Schema(example = "맛집 추천!")
    private String title;

    @Schema(example = "여기 맛있어요")
    private String content;

    @Schema(example = "jisu2298")
    private String authorId;

    @Schema(example = "1")
    private Long boardId;

    @Schema(example = "false")
    private boolean publicStatus;

    @Schema(example = "true")
    private boolean replyStatus;
}
