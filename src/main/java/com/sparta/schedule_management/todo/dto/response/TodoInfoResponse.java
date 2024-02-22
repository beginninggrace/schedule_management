package com.sparta.schedule_management.todo.dto.response;

import com.sparta.schedule_management.todo.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TodoInfoResponse {
    private String username;
    private String title;
    private String content;
    private LocalDateTime createAt;

    public TodoInfoResponse(String title, String content, LocalDateTime createAt, String username) {
        this.username = username;
        this.title = title;
        this.content = content;
        this.createAt = createAt;
    }

}
