package com.example.demo.Comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentRequest {

    @NotBlank(message = "작성자 ID는 필수입니다.")
    private final String writerId;

    @NotBlank(message = "내용은 필수입니다.")
    private final String content;

}
