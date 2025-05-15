package com.example.demo.comment.service;

import com.example.demo.comment.dto.request.CommentsRequest;
import com.example.demo.comment.dto.response.CommentsResponse;
import com.example.demo.comment.entity.CommentEntity;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.schedule.entity.SchedulesEntity;
import com.example.demo.schedule.repository.ScheduleRepository;
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
    public CommentsResponse createComment(Long scheduleId, CommentsRequest request) {
        SchedulesEntity schedulesEntity = scheduleRepository.findById(scheduleId).get();
        CommentEntity newComment = new CommentEntity(
            schedulesEntity,
            null,
            request.getWriterId(),
            request.getContent()
        );
        CommentEntity savedComment = commentRepository.save(newComment);
        return toResponse(savedComment);
    }

    // 일정별 댓글 목록 조회
    public List<CommentsResponse> getCommentsAll(Long scheduleId) {
        List<CommentEntity> list = commentRepository
            .findByScheduleIdAndParentCommentIsNullOrderByCreatedAtAsc(scheduleId);
        List<CommentsResponse> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CommentEntity commentEntity = list.get(i);
            CommentsResponse commentResponse = new CommentsResponse(
                commentEntity.getId(),
                commentEntity.getWriterId(),
                commentEntity.getContent(),
                commentEntity.getCreatedAt(),
                commentEntity.getUpdatedAt()
            );
            result.add(commentResponse);
        }
        return result;
    }

    // 단일 댓글 조회
    public CommentsResponse getComment(Long commentId) {
        CommentEntity commentEntity = commentRepository.findById(commentId).get();
        return toResponse(commentEntity);
    }

    // 댓글 수정
    public CommentsResponse updateComment(Long commentId, CommentsRequest request) {
        CommentEntity commentEntity = commentRepository.findById(commentId).get();
        commentEntity.updateContent(request.getContent());
        CommentEntity updatedComment = commentRepository.save(commentEntity);
        return toResponse(updatedComment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }


    // 대댓글 작성
    public CommentsResponse createreply(Long parentCommentId, CommentsRequest request) {

        boolean exists = commentRepository.existsByParentCommentId(parentCommentId);
        if (exists) {
            throw new IllegalStateException("한 댓글당 대댓글은 하나만 허용됩니다.");
        }

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

    // 대댓글 조회
    public List<CommentsResponse> getReplies(Long parentCommentId) {
        List<CommentEntity> list = commentRepository
            .findByParentCommentIdOrderByCreatedAtAsc(parentCommentId);

        List<CommentsResponse> responses = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            responses.add(toResponse(list.get(i)));
        }

        return responses;
    }

    // 대댓글 수정
    public CommentsResponse updateReply(Long parentCommentId, Long replyId,
        CommentsRequest request) {
        CommentEntity reply = commentRepository.findById(replyId).get();

        if (reply.getParentComment() == null
            || !reply.getParentComment().getId().equals(parentCommentId)) {
            throw new IllegalArgumentException
                ("잘못된 대댓글 식별자입니다.");
        }

        reply.updateContent(request.getContent());

        CommentEntity updatedReply = commentRepository.save(reply);
        return toResponse(updatedReply);
    }

    // 대댓글 삭제
    public void deleteReply(Long parentCommentId, Long replyId) {
        CommentEntity reply = commentRepository.findById(replyId).get();

        if (reply.getParentComment() == null
            || !reply.getParentComment().getId().equals(parentCommentId)) {
            throw new IllegalArgumentException(
                ("잘못된 대댓글 식별자입니다."));
        }

        commentRepository.deleteById(replyId);
    }

    // Entity → DTO 변환
    private CommentsResponse toResponse(CommentEntity commentEntity) {
        return new CommentsResponse(
            commentEntity.getId(),
            commentEntity.getWriterId(),
            commentEntity.getContent(),
            commentEntity.getCreatedAt(),
            commentEntity.getUpdatedAt()
        );
    }
}
