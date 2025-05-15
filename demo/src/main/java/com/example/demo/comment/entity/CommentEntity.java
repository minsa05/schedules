package com.example.demo.comment.entity;

import com.example.demo.BaseEntity;
import com.example.demo.schedule.entity.SchedulesEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Getter
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private SchedulesEntity schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private CommentEntity parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createdAt asc")
    private List<CommentEntity> replies = new ArrayList<>();

    @Column(length = 25, nullable = false)
    private String writerId;

    @Column(nullable = false)
    private String content;

    public CommentEntity(SchedulesEntity schedule, CommentEntity parentComment, String writerId,
        String content) {
        this.schedule = schedule;
        this.parentComment = parentComment;
        this.writerId = writerId;
        this.content = content;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}