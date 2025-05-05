package com.example.honbabspring.comment.repository;

import com.example.honbabspring.comment.entity.Comment;
import com.example.honbabspring.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(
            value = "select count(*) from (" +
                    "   select comment_id from comment " +
                    "   where post_id = :postId and parent_comment_id = :parentCommentId " +
                    "   limit :limit" +
                    ") t",
            nativeQuery = true
    )
    Long countBy(
            @Param("postId") Long postId,
            @Param("parentCommentId") Long parentCommentId,
            @Param("limit") Long limit
    );

    @Query(
            value = "select comment.comment_id, comment.content, comment.parent_comment_id, comment.post_id, " +
                    "comment.writer_id, comment.deleted, comment.created_at " +
                    "from (" +
                    "   select comment_id from comment where post_id = :postId " +
                    "   order by parent_comment_id asc, comment_id asc " +
                    "   limit :limit offset :offset " +
                    ") t left join comment on t.comment_id = comment.comment_id",
            nativeQuery = true
    )
    List<Comment> findAll(
            @Param("postId") Long postId,
            @Param("offset") Long offset,
            @Param("limit") Long limit
    );

    @Query(
            value = "select count(*) from (" +
                    "   select comment_id from comment where post_id = :postId limit :limit" +
                    ") t",
            nativeQuery = true
    )
    Long count(
            @Param("postId") Long postId,
            @Param("limit") Long limit
    );

    @Query(
            value = "select comment.comment_id, comment.content, comment.parent_comment_id, comment.post_id, " +
                    "comment.writer_id, comment.deleted, comment.created_at " +
                    "from comment " +
                    "where post_id = :postId " +
                    "order by parent_comment_id asc, comment_id asc " +
                    "limit :limit",
            nativeQuery = true
    )
    List<Comment> findAllInfiniteScroll(
            @Param("postId") Long postId,
            @Param("limit") Long limit
    );

    @Query(
            value = "select comment.comment_id, comment.content, comment.parent_comment_id, comment.post_id, " +
                    "comment.writer_id, comment.deleted, comment.created_at " +
                    "from comment " +
                    "where post_id = :postId and (" +
                    "   parent_comment_id > :lastParentCommentId or " +
                    "   (parent_comment_id = :lastParentCommentId and comment_id > :lastCommentId) " +
                    ")" +
                    "order by parent_comment_id asc, comment_id asc " +
                    "limit :limit",
            nativeQuery = true
    )
    List<Comment> findAllInfiniteScroll(
            @Param("postId") Long postId,
            @Param("lastParentCommentId") Long lastParentCommentId,
            @Param("lastCommentId") Long lastCommentId,
            @Param("limit") Long limit
    );


    @Query("select count(c) from CommentV2 c where c.post = :post")
    long countByPost(@Param("post") Post post);


}

