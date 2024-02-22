package com.sparta.schedule_management.todo.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class TodoUpdateRequest {
    private String title;
    private String content;
    @NotBlank
    private String password;
}
