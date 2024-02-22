package com.sparta.schedule_management.user.entity;

import com.sparta.schedule_management.todo.entity.Todo;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Todo> todoList = new ArrayList<>();


    //user가 todo리스트를 조회하는게 잦으면 @OneToMany mapping해야함
    //@OneToMany(mappedBy = "todo", cascade = CascadeType.REMOVE)
    //private List<Todo> todoList;


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

