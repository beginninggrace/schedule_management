package com.sparta.schedule_management.schedule.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponse { // 비밀번호같은 민감사항 노출하지 않기 위해 respponseDto 따로 생성

    private Long id;
    private String title;
    private String contents;
    private String user;
    private LocalDateTime createdWhen;


    public ScheduleResponse(Long id, String title, String contents, String user, LocalDateTime createdWhen) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.user = user;
        this.createdWhen = createdWhen;

    }
}
