package com.example.demo.comment.repository;

import com.example.demo.comment.entity.CommentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    boolean existsByParentCommentId(Long parentCommentId);

    List<CommentEntity> findByScheduleIdAndParentCommentIsNullOrderByCreatedAtAsc(Long scheduleId);

    List<CommentEntity> findByParentCommentIdOrderByCreatedAtAsc(Long parentCommentId);
}
