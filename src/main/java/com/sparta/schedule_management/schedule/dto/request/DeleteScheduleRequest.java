package com.sparta.schedule_management.schedule.dto.request;

import lombok.Getter;

@Getter
public class DeleteScheduleRequest {

    private String title;
    private String contents;
    private String user;
    private String password;
}
