package com.sparta.schedule_management.todo.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoListResponse {
    private String username;
    private String title;
    private LocalDateTime createAt;
    private Boolean completed;

    public TodoListResponse(String username, String title, LocalDateTime createAt, Boolean completed) {
        this.username = username;
        this.title = title;
        this.createAt = createAt;
        this.completed = completed;
    }

}
