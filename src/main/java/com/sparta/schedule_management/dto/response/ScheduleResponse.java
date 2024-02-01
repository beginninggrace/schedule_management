package com.sparta.schedule_management.dto.response;

import lombok.Getter;

@Getter
public class ScheduleResponse {

    private Long id;
    private String title;
    private String contents;
    private String user;
    private String password;


    public ScheduleResponse(Long id, String title, String contents, String user, String password) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.user = user;
        this.password = password;
    }
}
