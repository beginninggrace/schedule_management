package com.sparta.schedule_management.todo.dto.response;

import com.sparta.schedule_management.todo.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

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

    public TodoListResponse(Todo todo) { //
        this.title = todo.getTitle();
        this.createAt = todo.getCreateAt();
        this.completed = todo.getCompleted();
    }


//    public TodoListResponse(List<Todo> todoList) {
//        for (int i = 0; i < todoList.size(); i++) {
//            this.username = todoList.get(i).getUser().getUsername();
//
//        }
//    }
}
