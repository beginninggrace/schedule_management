package com.sparta.schedule_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String user; // 담당자
    private String title; // 할일 제목
    private String contents; // 할일 내용
    private String password; // 비밀번호


    public Schedule(String user, String title, String contents, String password) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.password = password;
    }

    public void updateSchedule(String user, String title, String contents, String password) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.password = password;
    }
}
