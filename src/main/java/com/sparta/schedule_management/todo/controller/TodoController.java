package com.sparta.schedule_management.todo.controller;

import com.sparta.schedule_management.todo.dto.request.TodoRequest;
import com.sparta.schedule_management.todo.dto.request.TodoUpdateRequest;
import com.sparta.schedule_management.todo.dto.response.TodoInfoResponse;
import com.sparta.schedule_management.todo.dto.response.TodoListResponse;
import com.sparta.schedule_management.todo.service.TodoService;
import com.sparta.schedule_management.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    // 할일 작성(본인만 가능)
    @PostMapping
    public void createTodo(@RequestBody TodoRequest todoRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoService.createTodo(todoRequest, userDetails.getUser());
        //  ResponseEntity자체가 엔티티라면 responsedto 엔티티 조회하는거 아닌가?
    }

    // 할일 단건 조희(사용자 모두가 가능)
    // - 본인만 가능할 때/가입 안한 모두가 가능할 때도 알아보기
    // A : 그게 아니라 하나의 투두 게시글을 조회할 때 원하는 특정한 글에 들어가기 위해(유니크한 값이 아닌 제목의 이름이 다 똑같으면 어느 투두인지 알 수 없기 때문에) api는 글의 시퀸스값인 todoId가 들어가는 것
    @GetMapping("/{todoId}")
    public TodoInfoResponse getTodo(@PathVariable Long todoId) {
        return todoService.getTodo(todoId);

    }

    // 할일 전체 조회(사용자 모두가 가능)
    @GetMapping
    public List<TodoListResponse> getTodos() {
        return todoService.getTodos();
//        List<User> todos = todoService.getTodos();
//        List<TodoListResponse> listResponses = todos.stream().map(TodoListResponse::new).toList();

    }

    // 할일 수정(본인만 가능)
    @PutMapping("/{todoId}")
    public TodoInfoResponse updateTodo(@PathVariable Long todoId, @RequestBody TodoUpdateRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.updateTodo(todoId, request, userDetails.getUser());
    }

    // 할일 완료
    @PatchMapping("/{todoId}/complete")
    public TodoListResponse completeTodo(@PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return todoService.completeTodo(todoId, userDetails.getUser());
    }

}
