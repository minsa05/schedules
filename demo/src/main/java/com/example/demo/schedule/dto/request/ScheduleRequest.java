package com.example.demo.schedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScheduleRequest {

    @NotBlank(message = "작성자 아이디는 필수입니다.")
    private final String writerId;

    @NotBlank(message = "제목은 필수입니다.")
    private final String title;

    private final String content;

}
