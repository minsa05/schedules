package com.example.demo.schedule.service;

import com.example.demo.schedule.dto.request.ScheduleRequest;
import com.example.demo.schedule.dto.response.ScheduleResponse;
import com.example.demo.schedule.entity.SchedulesEntity;
import com.example.demo.schedule.repository.ScheduleRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;


    // 생성
    public ScheduleResponse create(ScheduleRequest scheduleRequest) {
        SchedulesEntity newSchedule = new SchedulesEntity(
            scheduleRequest.getWriterId(),
            scheduleRequest.getTitle(),
            scheduleRequest.getContent()
        );
        SchedulesEntity savedSchedule = scheduleRepository.save(newSchedule);
        return toResponse(savedSchedule);
    }

    // 전체 조회
    public List<ScheduleResponse> findAll() {
        List<SchedulesEntity> schedules = scheduleRepository.findAll();
        List<ScheduleResponse> respons = new ArrayList<>();
        for (int i = 0; i < schedules.size(); i++) {
            respons.add(toResponse(schedules.get(i)));
        }
        return respons;
    }

    // 단일 조회
    public ScheduleResponse findById(Long id) {
        SchedulesEntity schedule = scheduleRepository.findById(id).get();
        return toResponse(schedule);
    }

    // 수정
    public ScheduleResponse update(Long id, ScheduleRequest scheduleRequest) {
        SchedulesEntity schedule = scheduleRepository.findById(id).get();
        schedule.update(scheduleRequest.getTitle(), scheduleRequest.getContent());
        SchedulesEntity updatedSchedule = scheduleRepository.save(schedule);
        return toResponse(updatedSchedule);
    }

    // 삭제
    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }


    // 엔티티 → DTO 변환
    private ScheduleResponse toResponse(SchedulesEntity schedule) {
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
