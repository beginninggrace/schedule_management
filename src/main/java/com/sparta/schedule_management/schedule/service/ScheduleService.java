package com.sparta.schedule_management.schedule.service;

import com.sparta.schedule_management.schedule.dto.request.DeleteScheduleRequest;
import com.sparta.schedule_management.schedule.dto.request.SaveScheduleRequest;
import com.sparta.schedule_management.schedule.dto.request.UpdateScheduleRequest;
import com.sparta.schedule_management.schedule.dto.response.ScheduleResponse;
import com.sparta.schedule_management.schedule.entity.Schedule;
import com.sparta.schedule_management.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public List<ScheduleResponse> getSchedules() { // 전체조회 - 작성일 기준(Sort.by), 내림차순 정렬(.descending())
        return scheduleRepository.findAll(Sort.by("createdWhen").descending()).stream().map(schedule -> new ScheduleResponse(schedule.getId(), schedule.getTitle(), schedule.getContents(), schedule.getUser(), schedule.getCreatedWhen())).collect(Collectors.toList());
    }

    public ScheduleResponse getSchedule(Long scheduleId) { // 단건조회
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalArgumentException("해당 아이디는 존재하지 않습니다."));
        return new ScheduleResponse(schedule.getId(), schedule.getTitle(), schedule.getContents(), schedule.getUser(), schedule.getCreatedWhen());
    }

    @Transactional
    public ScheduleResponse saveSchedule(SaveScheduleRequest request) { // 저장
        Schedule schedule = new Schedule(request.getUser(), request.getTitle(), request.getContents(), request.getPassword());
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponse(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents(), savedSchedule.getUser(), schedule.getCreatedWhen());
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long scheduleId, UpdateScheduleRequest request) { // 수정
        Schedule schedule = scheduleRepository.findByIdAndPassword(scheduleId, request.getPassword()).orElseThrow(() -> new IllegalArgumentException("계정 정보가 일치하지 않습니다."));
        schedule.updateSchedule(request.getUser(), request.getTitle(), request.getContents());
        return new ScheduleResponse(schedule.getId(), schedule.getTitle(), schedule.getContents(), schedule.getUser(), schedule.getCreatedWhen());
    }

    @Transactional
    public void deleteSchedule(Long scheduleId, DeleteScheduleRequest request) { // 삭제
        Schedule schedule = scheduleRepository.findByIdAndPassword(scheduleId, request.getPassword()).orElseThrow(() -> new IllegalArgumentException("계정 정보가 일치하지 않습니다."));
        scheduleRepository.deleteById(scheduleId);
    }
}
