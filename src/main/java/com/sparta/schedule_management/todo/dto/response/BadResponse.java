package com.sparta.schedule_management.todo.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BadResponse {
        private String message;
        private Integer statusCode;

    public BadResponse(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
