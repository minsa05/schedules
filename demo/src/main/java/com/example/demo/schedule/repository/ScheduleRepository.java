package com.example.demo.schedule.repository;

import com.example.demo.schedule.entity.SchedulesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<SchedulesEntity, Long> {


}
