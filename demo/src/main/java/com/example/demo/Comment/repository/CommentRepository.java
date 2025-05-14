package com.example.demo.Comment.repository;

import com.example.demo.Comment.Entity.CommentEntity;
import com.example.demo.Schedule.entity.ScheduleEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    long countByScheduleAndParentCommentIsNull(ScheduleEntity schedule);

    List<CommentEntity> findByScheduleAndParentCommentIsNullOrderByCreatedAtAsc(
        ScheduleEntity schedule);

    List<CommentEntity> findByParentCommentOrderByCreatedAtAsc(CommentEntity parentComment);
}
