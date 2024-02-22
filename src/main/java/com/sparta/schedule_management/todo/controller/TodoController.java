package com.sparta.schedule_management.todo.controller;

import com.sparta.schedule_management.todo.dto.response.TodoInfoResponse;
import com.sparta.schedule_management.todo.dto.request.TodoRequest;
import com.sparta.schedule_management.todo.dto.response.TodoListResponse;
import com.sparta.schedule_management.todo.entity.Todo;
import com.sparta.schedule_management.todo.service.TodoService;
import com.sparta.schedule_management.user.entity.User;
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

        // return new ResponseEntity<>(new TodoResponse(todo), HttpStatus.CREATED); -> // CREATED하면 url을 넣어야 한다는데 그게 무엇?
                                                                                       // 이런 식으로 쓰면 resposedto로 넘겨주는게 아니라 엔티티를 그대로 반환하는것? - ResponseEntity자체가 엔티티라면 위에꺼 써도 엔티티 조회하는거 아닌가?
    }

    // 할일 단건 조희(사용자 모두가 가능)
    // - 본인만 가능할 때/가입 안한 모두가 가능할 때도 알아보기
    // A : 그게 아니라 하나의 투두 게시글을 조회할 때 원하는 특정한 글에 들어가기 위해(유니크한 값이 아닌 제목의 이름이 다 똑같으면 어느 투두인지 알 수 없기 때문에) api는 글의 시퀸스값인 todoId가 들어가는 것
    @GetMapping("/{todoId}")
    public TodoInfoResponse getTodo(@PathVariable Long todoId) {
        return todoService.getTodo(todoId); // postman에서 clint가 받는 데이터가 null인건 안 주고 싶은데 어떻게 해야할지 모름

    }

    //할일 전체 조회(사용자 모두가 가능)
    @GetMapping
    public List<TodoListResponse> getTodos() {
        return todoService.getTodos();
//        List<User> todos = todoService.getTodos();
//        List<TodoListResponse> listResponses = todos.stream().map(TodoListResponse::new).toList();

    }



//    // 할일 수정(본인만 가능)
//    @PutMapping
//    public ResponseEntity<ProfileResponseDto> updateProfile(@RequestBody UserUpdateDto request, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        ProfileResponseDto response = userService.updateProfile(userDetails.getUser().getUserNum(), request);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//
//    // 할일 수정(본인만 가능)
//    @PutMapping("/{todoId}")
//    public ResponseEntity<TodoRequest> updateTodo(@PathVariable Long todoId, @RequestBody TodoRequestDTO todoRequestDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        try {
//            TodoRequest responseDTO = todoService.updateTodo(todoId, todoRequestDTO, userDetails.getUser());
//            return ResponseEntity.ok().body(responseDTO);
//        } catch (RejectedExecutionException | IllegalArgumentException ex) {
//            return ResponseEntity.badRequest().body(new TodoResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
//        }
//    }
//
//
//    // 할일 완료
//    @PatchMapping("/{todoId}/complete")
//    public ResponseEntity<TodoResponse> completeTodo(@PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        try {
//            TodoResponse responseDTO = todoService.completeTodo(todoId, userDetails.getUser());
//            return ResponseEntity.ok().body(responseDTO);
//        } catch (RejectedExecutionException | IllegalArgumentException ex) {
//            return ResponseEntity.badRequest().body(new TodoResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
//        }
//    }
}
