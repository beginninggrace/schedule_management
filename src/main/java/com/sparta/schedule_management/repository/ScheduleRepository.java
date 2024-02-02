package com.sparta.schedule_management.repository;

import com.sparta.schedule_management.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findByIdAndPassword(Long scheduleId, String password);
}
