package com.example.demo.comment.controller;


import com.example.demo.comment.dto.request.CommentsRequest;
import com.example.demo.comment.dto.response.CommentsResponse;
import com.example.demo.comment.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments/{commentId}")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 단일 댓글 조회
    @GetMapping
    public CommentsResponse get(
        @PathVariable Long commentId
    ) {
        return commentService.getComment(commentId);
    }

    // 댓글 수정
    @PutMapping
    public CommentsResponse update(
        @PathVariable Long commentId,
        @Valid @RequestBody CommentsRequest request
    ) {
        return commentService.updateComment(commentId, request);
    }

    // 댓글 삭제
    @DeleteMapping
    public void delete(
        @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId);
    }

    // 대댓글 작성
    @PostMapping("/replies")
    public CommentsResponse reply(
        @PathVariable Long commentId,
        @Valid @RequestBody CommentsRequest request
    ) {
        return commentService.createreply(commentId, request);
    }

    // 대댓글 조회
    @GetMapping("/replies")
    public List<CommentsResponse> replies(
        @PathVariable Long commentId
    ) {
        return commentService.getReplies(commentId);
    }

    // 대댓글 수정
    @PutMapping("/replies/{replyId}")
    public CommentsResponse updateReply(
        @PathVariable Long commentId,
        @PathVariable Long replyId,
        @Valid @RequestBody CommentsRequest request
    ) {
        return commentService.updateReply(commentId, replyId, request);
    }

    // 대댓글 삭제
    @DeleteMapping("/replies/{replyId}")
    public ResponseEntity<Void> deleteReply(
        @PathVariable Long commentId,
        @PathVariable Long replyId
    ) {
        commentService.deleteReply(commentId, replyId);
        return ResponseEntity.noContent().build();
    }
}
