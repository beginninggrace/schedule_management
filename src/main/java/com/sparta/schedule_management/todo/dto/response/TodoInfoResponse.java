package com.sparta.schedule_management.todo.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoInfoResponse {
    private String username;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public TodoInfoResponse(String username, String title, String content, LocalDateTime createdAt) {
        this.username = username;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

}
