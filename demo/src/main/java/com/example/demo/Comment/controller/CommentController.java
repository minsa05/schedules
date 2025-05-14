package com.example.demo.Comment.controller;

import com.example.demo.Comment.dto.request.CommentRequest;
import com.example.demo.Comment.dto.response.CommentResponse;
import com.example.demo.Comment.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

    // 3) 단일 댓글 조회
    @GetMapping
    public CommentResponse get(
        @PathVariable Long commentId
    ) {
        return commentService.getCommentById(commentId);
    }

    // 4) 댓글 수정
    @PutMapping
    public CommentResponse update(
        @PathVariable Long commentId,
        @Valid @RequestBody CommentRequest request
    ) {
        return commentService.updateComment(commentId, request);
    }

    // 5) 댓글 삭제
    @DeleteMapping
    public void delete(
        @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId);
    }

    // 6) 대댓글 작성
    @PostMapping("/replies")
    public CommentResponse reply(
        @PathVariable Long commentId,
        @Valid @RequestBody CommentRequest request
    ) {
        return commentService.replyToComment(commentId, request);
    }

    // 7) 대댓글 조회
    @GetMapping("/replies")
    public List<CommentResponse> replies(
        @PathVariable Long commentId
    ) {
        return commentService.getRepliesByComment(commentId);
    }
}
