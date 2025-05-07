package com.example.honbabspring.like.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "post_like_count")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeCount {

    @Id
    private Long postId;
    private Long likeCount;
    @Version
    private Long version;

    public static PostLikeCount init(Long postId, Long likeCount) {
        PostLikeCount postLikeCount = new PostLikeCount();
        postLikeCount.postId = postId;
        postLikeCount.likeCount = likeCount;
        postLikeCount.version = 0L;
        return postLikeCount;
    }

    public void increase() {
        this.likeCount++;
    }

    public void decrease() {
        this.likeCount--;
    }

}
