package com.sparta.schedule_management.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    @Column(nullable = false)
    private String user; // 담당자
    @Column (nullable = false)
    private String title; // 할일 제목
    @Column (nullable = false)
    private String contents; // 할일 내용
    @Column (nullable = false)
    private String password; // 비밀번호
    @Column (nullable = false)
    private LocalDateTime createdWhen; // 작성일


    public Schedule(String user, String title, String contents, String password) {
        this.user = user;
        this.title = title;
        this.contents = contents;
        this.password = password;
        this.createdWhen = LocalDateTime.now();
    }

    public void updateSchedule(String user, String title, String contents) { // user, title, contents만 수정될 수 있게
        this.user = user;
        this.title = title;
        this.contents = contents;

    }
}
