package com.sparta.schedule_management.service;

import com.sparta.schedule_management.dto.request.SaveScheduleRequest;
import com.sparta.schedule_management.dto.request.UpdateScheduleRequest;
import com.sparta.schedule_management.dto.response.ScheduleResponse;
import com.sparta.schedule_management.entity.Schedule;
import com.sparta.schedule_management.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public List<ScheduleResponse> getSchedules() { // 전체조회
        return scheduleRepository.findAll().stream().map(schedule -> new ScheduleResponse(schedule.getId(), schedule.getTitle(), schedule.getContents(), schedule.getUser(), schedule.getPassword())).collect(Collectors.toList());
    }

    public ScheduleResponse getSchedule(Long scheduleId) { // 단건조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("해당 아이디는 존재하지 않습니다."));
        return new ScheduleResponse(schedule.getId(), schedule.getTitle(), schedule.getContents(), schedule.getUser(), schedule.getPassword());
    }

    @Transactional
    public ScheduleResponse saveSchedule(SaveScheduleRequest request) { // 저장
        Schedule schedule = new Schedule(request.getUser(), request.getTitle(), request.getContents(), request.getPassword());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponse(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents(), savedSchedule.getUser(), savedSchedule.getPassword());
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) { // 수정
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("해당 아이디는 존재하지 않습니다."));
        schedule.updateSchedule(request.getUser(), request.getTitle(), request.getContents(), request.getPassword());
        return new ScheduleResponse(schedule.getId(), schedule.getTitle(), schedule.getContents(), schedule.getUser(), schedule.getPassword());
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) { // 삭제
        scheduleRepository.deleteById(scheduleId);
    }
}
