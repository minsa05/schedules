package com.example.demo.Schedule.service;

import com.example.demo.Schedule.dto.request.ScheduleRequest;
import com.example.demo.Schedule.dto.response.ScheduleResponse;
import com.example.demo.Schedule.entity.ScheduleEntity;
import com.example.demo.Schedule.repository.ScheduleRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;


    // 생성
    public ScheduleResponse create(ScheduleRequest scheduleRequest) {
        ScheduleEntity newSchedule = new ScheduleEntity(
            scheduleRequest.getWriterId(),
            scheduleRequest.getTitle(),
            scheduleRequest.getContent()
        );
        ScheduleEntity savedSchedule = scheduleRepository.save(newSchedule);
        return toResponse(savedSchedule);
    }

    // 전체 조회
    public List<ScheduleResponse> findAll() {
        List<ScheduleEntity> schedules = scheduleRepository.findAll();
        List<ScheduleResponse> respons = new ArrayList<>();
        for (int i = 0; i < schedules.size(); i++) {
            respons.add(toResponse(schedules.get(i)));
        }
        return respons;
    }

    // 단일 조회
    public ScheduleResponse findById(Long id) {
        ScheduleEntity schedule = scheduleRepository.findById(id).get();
        return toResponse(schedule);
    }

    // 수정
    public ScheduleResponse update(Long id, ScheduleRequest scheduleRequest) {
        ScheduleEntity schedule = scheduleRepository.findById(id).get();
        schedule.update(scheduleRequest.getTitle(), scheduleRequest.getContent());
        ScheduleEntity updatedSchedule = scheduleRepository.save(schedule);
        return toResponse(updatedSchedule);
    }

    // 삭제
    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }


    // 엔티티 → DTO 변환
    private ScheduleResponse toResponse(ScheduleEntity schedule) {
        return new ScheduleResponse(
            schedule.getId(),
            schedule.getWriterId(),
            schedule.getTitle(),
            schedule.getContent(),
            schedule.getCreatedAt(),
            schedule.getUpdatedAt(),
            0L // 댓글 기능 미포함
        );
    }
}
