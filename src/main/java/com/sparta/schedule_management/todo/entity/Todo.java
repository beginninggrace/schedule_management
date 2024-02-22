package com.sparta.schedule_management.todo.entity;

import com.sparta.schedule_management.todo.dto.request.TodoRequest;
import com.sparta.schedule_management.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
    private LocalDateTime createAt;

    @Column
    private Boolean completed = false;


    @ManyToOne() // 유저는 여러개의 게시글을 작성할 수 있기 때문에
    @JoinColumn(name = "user_id") // todo가 many쪽이니 joincolumn 넣어주기
    private User user; // 연관관계 표시


    public Todo(String title, String content, User findUser) {
        this.title = title;
        this.content = content;
        this.user = findUser;
        this.createAt = LocalDateTime.now();
    }

    public Todo(TodoRequest dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.createAt = LocalDateTime.now();
    }

    // 연관관계 mapping 메소드
//    public void setUser(User user) {
//        this.user = user;
//        user.getTodoList().add(this);
//    }

    public void updateTodo(String title, String content) {
        this.title = title;
        this.content =content;
    }
}
