package com.example.honbabspring.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleUpdateRequestDto {
    private String title;
    private String content;
}
