package com.sparta.schedule_management.common;

import lombok.AllArgsConstructor;
import lombok.Builder;

@AllArgsConstructor
public class CommonResponse<T> {
    private int status;
    private String message;
    private T body;

    public static <T> CommonResponse<T> of (int status, String message, T body) {
        return new CommonResponse<>(status, message, body);
    }

}
