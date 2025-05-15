package com.example.demo.schedule.controller;

import com.example.demo.schedule.dto.request.ScheduleRequest;
import com.example.demo.schedule.dto.response.ScheduleResponse;
import com.example.demo.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService ScheduleService;

    // 생성
    @PostMapping
    public ScheduleResponse create(@Valid @RequestBody ScheduleRequest scheduleRequest) {
        return ScheduleService.create(scheduleRequest);
    }

    // 전체 조회
    @GetMapping
    public List<ScheduleResponse> AllSchedules() {
        return ScheduleService.findAll();
    }

    // 단일 조회
    @GetMapping("/{id}")
    public ScheduleResponse SchedulesId(@PathVariable Long id) {
        return ScheduleService.findById(id);
    }

    // 수정
    @PutMapping("/{id}")
    public ScheduleResponse update(
        @PathVariable Long id,
        @Valid @RequestBody ScheduleRequest scheduleRequest
    ) {
        return ScheduleService.update(id, scheduleRequest);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ScheduleService.delete(id);
    }
}
