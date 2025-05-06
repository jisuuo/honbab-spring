//package com.example.honbabspring.Comment.Service;
//
//import com.example.honbabspring.comment.entity.Comment;
//import com.example.honbabspring.comment.repository.CommentRepository;
//import com.example.honbabspring.comment.service.CommentService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class) // ✅ 수정됨
//class CommentServiceTest {
//    @InjectMocks
//    CommentService commentService;
//
//    @Mock
//    CommentRepository commentRepository;
//
//    @Test
//    @DisplayName("삭제할 댓글이 자식이 있으면, 삭제 표시만 한다.")
//    void deleteShouldMarkDeletedIfHasChildren() {
//        // given
//        Long postId = 1L;
//        Long commentId = 2L;
//        Comment comment = createComment(postId, commentId);
//        given(commentRepository.findById(commentId))
//                .willReturn(Optional.of(comment));
//        given(commentRepository.countBy(postId, commentId, 2L)).willReturn(2L);
//
//        // when
//        commentService.delete(commentId);
//
//        // then
//        verify(comment).delete();
//    }
//
//
//    @Test
//    @DisplayName("하위 댓글이 삭제되고, 삭제되지 않은 부모면, 하위 댓글만 삭제한다.")
//    void deleteShouldDeleteChildOnlyIfNotDeletedParent() {
//        // given
//        Long postId = 1L;
//        Long commentId = 2L;
//        Long parentCommentId = 1L;
//
//        Comment comment = createComment(postId, commentId, parentCommentId);
//        given(comment.isRoot()).willReturn(false);
//
//        Comment parentComment = mock(Comment.class);
//        given(parentComment.getDeleted()).willReturn(false);
//
//        System.out.println("commentRepository = " + commentRepository);
//
//        given(commentRepository.findById(commentId))
//                .willReturn(Optional.of(comment));
//        given(commentRepository.countBy(postId, commentId, 2L)).willReturn(1L);
//
//        given(commentRepository.findById(parentCommentId))
//                .willReturn(Optional.of(parentComment));
//
//        // when
//        commentService.delete(commentId);
//
//        // then
//        verify(commentRepository).delete(comment);
//        verify(commentRepository, never()).delete(parentComment);
//    }
//
//    @Test
//    @DisplayName("하위 댓글이 삭제되고, 삭제된 부모면, 재귀적으로 모두 삭제한다.")
//    void deleteShouldDeleteAllRecursivelyIfDeletedParent() {
//        // given
//        Long postId = 1L;
//        Long commentId = 2L;
//        Long parentCommentId = 1L;
//
//        Comment comment = createComment(postId, commentId, parentCommentId);
//        given(comment.isRoot()).willReturn(false);
//
//        Comment parentComment = createComment(postId, parentCommentId);
//        given(parentComment.isRoot()).willReturn(true);
//        given(parentComment.getDeleted()).willReturn(true);
//
//        given(commentRepository.findById(commentId))
//                .willReturn(Optional.of(comment));
//        given(commentRepository.countBy(postId, commentId, 2L)).willReturn(1L);
//
//        given(commentRepository.findById(parentCommentId))
//                .willReturn(Optional.of(parentComment));
//        given(commentRepository.countBy(postId, parentCommentId, 2L)).willReturn(1L);
//
//        // when
//        commentService.delete(commentId);
//
//        // then
//        verify(commentRepository).delete(comment);
//        verify(commentRepository).delete(parentComment);
//    }
//
//    private Comment createComment(Long postId, Long commentId) {
//        Comment comment  = mock(Comment.class);
//        given(comment.getPost()).willReturn(postId);
//        given(comment.getCommentId()).willReturn(commentId);
//        return comment;
//    }
//
//    private Comment createComment(Long postId, Long commentId, Long parentCommentId) {
//        Comment comment = createComment(postId, commentId);
//        given(comment.getParentCommentId()).willReturn(parentCommentId);
//        return comment;
//    }
//
//}