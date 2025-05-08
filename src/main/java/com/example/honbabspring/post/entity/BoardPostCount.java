package com.example.honbabspring.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "board_post_count")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardPostCount {
    @Id
    private Long boardId;
    private Long postCount;

    public static BoardPostCount init(Long boardId, Long postCount) {
        BoardPostCount boardPostCount = new BoardPostCount();
        boardPostCount.boardId = boardId;
        boardPostCount.postCount = postCount;
        return boardPostCount;
    }

}
