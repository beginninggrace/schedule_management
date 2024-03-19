package com.sparta.schedule_management.todo.controller;

import com.sparta.schedule_management.common.CommonResponse;
import com.sparta.schedule_management.todo.dto.request.TodoRequest;
import com.sparta.schedule_management.todo.dto.request.TodoUpdateRequest;
import com.sparta.schedule_management.todo.dto.response.TodoInfoResponse;
import com.sparta.schedule_management.todo.dto.response.TodoListResponse;
import com.sparta.schedule_management.todo.service.TodoService;
import com.sparta.schedule_management.user.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    // 할일 작성(본인만 가능)
    @PostMapping
    public void createTodo(
        @RequestBody TodoRequest todoRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoService.createTodo(todoRequest, userDetails.getUser());
    }

    @GetMapping("/{todoId}")
    public CommonResponse<TodoInfoResponse> getTodo(
        @PathVariable Long todoId) {
        return CommonResponse.of(200, "단건 조회 성공", todoService.getTodo(todoId));

    }

    // 할일 전체 조회(사용자 모두가 가능)
    @GetMapping
    public List<TodoListResponse> getTodos(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return todoService.getTodos(page, size);
    }

    // 할일 수정(본인만 가능)
    @PutMapping("/{todoId}")
    public TodoInfoResponse updateTodo(
        @PathVariable Long todoId,
        @RequestBody TodoUpdateRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.updateTodo(todoId, request, userDetails.getUser());
    }

    // 할일 완료
    @PatchMapping("/{todoId}/complete")
    public TodoListResponse completeTodo(
        @PathVariable Long todoId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.completeTodo(todoId, userDetails.getUser());
    }

    // 검색
    @GetMapping("/search/keyword")
    public List<TodoListResponse> searchKeyword(
        @RequestParam String q) {
        return todoService.searchKeyword(q);
    }


}
