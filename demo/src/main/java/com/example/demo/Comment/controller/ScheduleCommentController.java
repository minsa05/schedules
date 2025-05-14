package com.example.demo.Comment.controller;

import com.example.demo.Comment.dto.request.CommentRequest;
import com.example.demo.Comment.dto.response.CommentResponse;
import com.example.demo.Comment.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class ScheduleCommentController {

    private final CommentService commentService;

    // 1) 댓글 작성
    @PostMapping
    public CommentResponse create(
        @PathVariable Long scheduleId,
        @Valid @RequestBody CommentRequest request
    ) {
        return commentService.createComment(scheduleId, request);
    }

    // 2) 댓글 목록 조회
    @GetMapping
    public List<CommentResponse> list(
        @PathVariable Long scheduleId
    ) {
        return commentService.getCommentsBySchedule(scheduleId);
    }
}
