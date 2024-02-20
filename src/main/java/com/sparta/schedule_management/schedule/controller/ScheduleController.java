package com.sparta.schedule_management.schedule.controller;

import com.sparta.schedule_management.schedule.dto.request.DeleteScheduleRequest;
import com.sparta.schedule_management.schedule.dto.request.SaveScheduleRequest;
import com.sparta.schedule_management.schedule.dto.request.UpdateScheduleRequest;
import com.sparta.schedule_management.schedule.dto.response.ScheduleResponse;
import com.sparta.schedule_management.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;


    @GetMapping // 전체조회
    public List<ScheduleResponse> getSchedules() {

        return scheduleService.getSchedules();
    }

    @GetMapping("/{scheduleId}") // 단건조회
    public ScheduleResponse getSchedule(@PathVariable Long scheduleId) {

        return scheduleService.getSchedule(scheduleId);
    }

    @PostMapping // 저장
    public ScheduleResponse saveSchedule(@RequestBody SaveScheduleRequest request) {

        return scheduleService.saveSchedule(request);
    }

    @PutMapping("/{scheduleId}") // 수정
    public ScheduleResponse updateSchedule(@PathVariable Long scheduleId, @RequestBody UpdateScheduleRequest request) {

        return scheduleService.updateSchedule(scheduleId, request);
    }

    @DeleteMapping("/{scheduleId}") // 삭제
    public void deleteSchedule(@PathVariable Long scheduleId, @RequestBody DeleteScheduleRequest request) {

        scheduleService.deleteSchedule(scheduleId, request);
    }

}
