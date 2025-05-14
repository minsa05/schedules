package com.example.demo.Comment.service;

import com.example.demo.Comment.Entity.CommentEntity;
import com.example.demo.Comment.dto.request.CommentRequest;
import com.example.demo.Comment.dto.response.CommentResponse;
import com.example.demo.Comment.repository.CommentRepository;
import com.example.demo.Schedule.entity.ScheduleEntity;
import com.example.demo.Schedule.repository.ScheduleRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    // 댓글 생성
    public CommentResponse createComment(Long scheduleId, CommentRequest request) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(scheduleId).get();
        CommentEntity newComment = new CommentEntity(
            scheduleEntity,
            null,
            request.getWriterId(),
            request.getContent()
        );
        CommentEntity savedComment = commentRepository.save(newComment);
        return toResponse(savedComment);
    }

    // 일정별 댓글 목록 조회
    public List<CommentResponse> getCommentsBySchedule(Long scheduleId) {
        ScheduleEntity scheduleEntity = scheduleRepository.findById(scheduleId).get();
        List<CommentEntity> commentEntities = commentRepository
            .findByScheduleAndParentCommentIsNullOrderByCreatedAtAsc(scheduleEntity);
        List<CommentResponse> responses = new ArrayList<>();
        for (int i = 0; i < commentEntities.size(); i++) {
            responses.add(toResponse(commentEntities.get(i)));
        }
        return responses;
    }

    // 단일 댓글 조회
    public CommentResponse getCommentById(Long commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId).get();
        return toResponse(commentEntity);
    }

    // 댓글 수정
    public CommentResponse updateComment(Long commentId, CommentRequest request) {
        CommentEntity commentEntity = commentRepository.findById(commentId).get();
        commentEntity.updateContent(request.getContent());
        CommentEntity updatedComment = commentRepository.save(commentEntity);
        return toResponse(updatedComment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }


    // 6) 대댓글 작성
    public CommentResponse replyToComment(Long parentCommentId, CommentRequest request) {
        CommentEntity parent = commentRepository.findById(parentCommentId).get();
        CommentEntity reply = new CommentEntity(
            parent.getSchedule(),
            parent,
            request.getWriterId(),
            request.getContent()
        );
        CommentEntity savedReply = commentRepository.save(reply);
        return toResponse(savedReply);
    }

    // 7) 대댓글 목록 조회
    public List<CommentResponse> getRepliesByComment(Long parentCommentId) {
        CommentEntity parent = commentRepository.findById(parentCommentId).get();
        List<CommentEntity> replies = commentRepository
            .findByParentCommentOrderByCreatedAtAsc(parent);

        List<CommentResponse> responses = new ArrayList<>();
        for (int i = 0; i < replies.size(); i++) {
            responses.add(toResponse(replies.get(i)));
        }
        return responses;
    }


    // Entity → DTO 변환
    private CommentResponse toResponse(CommentEntity commentEntity) {
        return new CommentResponse(
            commentEntity.getId(),
            commentEntity.getWriterId(),
            commentEntity.getContent(),
            commentEntity.getCreatedAt(),
            commentEntity.getUpdatedAt()
        );
    }
}
