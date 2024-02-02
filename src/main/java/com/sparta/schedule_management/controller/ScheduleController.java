package com.sparta.schedule_management.controller;

import com.sparta.schedule_management.dto.request.DeleteScheduleRequest;
import com.sparta.schedule_management.dto.request.SaveScheduleRequest;
import com.sparta.schedule_management.dto.request.UpdateScheduleRequest;
import com.sparta.schedule_management.dto.response.ScheduleResponse;
import com.sparta.schedule_management.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;


    @GetMapping("/schedule") // 전체조회
    public List<ScheduleResponse> getSchedules() {

        return scheduleService.getSchedules();
    }

    @GetMapping("/schedule/{scheduleId}") // 단건조회
    public ScheduleResponse getSchedule(@PathVariable Long scheduleId) {

        return scheduleService.getSchedule(scheduleId);
    }

    @PostMapping("/schedule") // 저장
    public ScheduleResponse saveSchedule(@RequestBody SaveScheduleRequest request) {

        return scheduleService.saveSchedule(request);
    }

    @PutMapping("/schedule/{scheduleId}") // 수정
    public ScheduleResponse updateSchedule(@PathVariable Long scheduleId, @RequestBody UpdateScheduleRequest request) {

        return scheduleService.updateSchedule(scheduleId, request);
    }

    @DeleteMapping("/schedule/{scheduleId}") // 삭제
    public void deleteSchedule(@PathVariable Long scheduleId, @RequestBody DeleteScheduleRequest request) {

        scheduleService.deleteSchedule(scheduleId, request);
    }

}
