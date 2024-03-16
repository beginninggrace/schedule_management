package com.sparta.schedule_management.comment.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentResponse {

    private String username;
    private String contents;

    public CommentResponse(String username, String contents) {
        this.username = username;
        this.contents = contents;
    }
}
