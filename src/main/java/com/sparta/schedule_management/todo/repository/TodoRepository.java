package com.sparta.schedule_management.todo.repository;

import com.sparta.schedule_management.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
