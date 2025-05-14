package com.example.demo.Comment.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentResponse {

    private final Long id;
    private final String writerId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

}
