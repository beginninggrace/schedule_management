package com.sparta.schedule_management.todo.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoListResponse {
    private String username;
    private String title;
    private LocalDateTime createdAt;
    private Boolean completed;

    public TodoListResponse(String username, String title, LocalDateTime createdAt, Boolean completed) {
        this.username = username;
        this.title = title;
        this.createdAt = createdAt;
        this.completed = completed;

    }

}
