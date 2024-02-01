package com.sparta.schedule_management.repository;

import com.sparta.schedule_management.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
