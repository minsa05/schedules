package com.example.demo.comment.controller;


import com.example.demo.comment.dto.request.CommentsRequest;
import com.example.demo.comment.dto.response.CommentsResponse;
import com.example.demo.comment.service.CommentService;
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

    // 댓글 작성
    @PostMapping
    public CommentsResponse create(
        @PathVariable Long scheduleId,
        @Valid @RequestBody CommentsRequest request
    ) {
        return commentService.createComment(scheduleId, request);
    }

    // 댓글 전체 조회
    @GetMapping
    public List<CommentsResponse> list(
        @PathVariable Long scheduleId
    ) {
        return commentService.getCommentsAll(scheduleId);
    }
}
