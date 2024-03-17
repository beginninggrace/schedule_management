package com.sparta.schedule_management.todo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false) // @Lab
    private String content;

    @Column
    private LocalDateTime createdAt;

    @Column
    private Boolean completed = false;

    @Column
    private Long userId;


    public Todo(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }

    public void updateTodo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void completeTodo() {
        this.completed = true;
    }
}
