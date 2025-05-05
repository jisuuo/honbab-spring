package com.example.honbabspring.post.repository;

import com.example.honbabspring.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(
            value = "select post.post_id, post.title, post.content, post.board_id, post.writer_id, " +
                    "post.created_at, post.modified_at " +
                    "from (" +
                    "   select post_id from post " +
                    "   where board_id = :boardId " +
                    "   order by post_id desc " +
                    "   limit :limit offset :offset " +
                    ") t left join post on t.post_id = post.post_id ",
            nativeQuery = true
    )
    List<Post> findAll(
            @Param("boardId") Long boardId,
            @Param("offset") Long offset,
            @Param("limit") Long limit
    );

    @Query(
            value = "select count(*) from(" +
                    "   select post_id from post where board_id = :boardId limit :limit" +
                    ") t",
            nativeQuery = true
    )
    Long count(@Param("boardId") Long boardId, @Param("limit") Long limit);

    @Query(
            value = "select post.post_id, post.title, post.content, post.board_id, post.writer_id, " +
                    "post.created_at, post.modified_at " +
                    "from post" +
                    "where board_id =:boardId and post_id.id < :lastPostId" +
                    "order by post_id desc limit :limit",
            nativeQuery = true
    )
    List<Post> findAllInfiniteScroll(@Param("boardId") Long boardId, @Param("limit") Long limit);

    @Query(
            value = "select post.post_id, post.title, post.content, post.board_id, post.writer_id, " +
                    "post.created_at, post.modified_at " +
                    "from post" +
                    "where board_id =:boardId and post.id < :lastPostId" +
                    "order by post_id desc limit :limit",
            nativeQuery = true
    )
    List<Post> findAllInfiniteScroll(@Param("boardId") Long boardId, @Param("limit") Long limit, @Param("lastPostId") Long lastPostId);
}
