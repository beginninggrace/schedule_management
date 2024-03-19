package com.sparta.schedule_management.todo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRequest {
    private String title;
    private String content;
}
