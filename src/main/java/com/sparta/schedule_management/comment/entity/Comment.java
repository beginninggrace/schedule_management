package com.sparta.schedule_management.comment.entity;

import com.sparta.schedule_management.comment.dto.request.CommentRequest;
import com.sparta.schedule_management.comment.dto.request.UpdateCommentRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String username;
    @Column(nullable = false)
    private String contents;

    @Column
    private Long todoId;

    @Column
    private Long userId;


    public Comment(Long userId, Long todoId, CommentRequest request) {
        this.userId = userId;
        this.todoId = todoId;
        this.username = request.getUsername();
        this.contents = request.getContents();
    }

    public void updateComment(UpdateCommentRequest request, Long userId) {
        this.contents = request.getContents();
        this.userId = userId;
    }
}
