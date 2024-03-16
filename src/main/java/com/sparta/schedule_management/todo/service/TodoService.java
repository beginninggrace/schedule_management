package com.sparta.schedule_management.todo.service;

import com.sparta.schedule_management.todo.dto.request.TodoRequest;
import com.sparta.schedule_management.todo.dto.request.TodoUpdateRequest;
import com.sparta.schedule_management.todo.dto.response.TodoInfoResponse;
import com.sparta.schedule_management.todo.dto.response.TodoListResponse;
import com.sparta.schedule_management.user.entity.User;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface TodoService {

    // 할일 작성
    void createTodo(TodoRequest request, User user);

    // 할일 단건 조회
    TodoInfoResponse getTodo(Long todoId);

    // 할일 전체 조회
    List<TodoListResponse> getTodos();

    // 할일 수정
    @Transactional
    TodoInfoResponse updateTodo(Long todoId, TodoUpdateRequest request, User user);

    // 할일 완료
    @Transactional
    TodoListResponse completeTodo(Long todoId, User user);
}
