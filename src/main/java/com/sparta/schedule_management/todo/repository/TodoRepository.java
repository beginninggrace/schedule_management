package com.sparta.schedule_management.todo.repository;

import com.sparta.schedule_management.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TodoRepository extends JpaRepository<Todo, Long>,
    PagingAndSortingRepository<Todo, Long> {



}
