package com.sparta.schedule_management.todo.service;

import com.sparta.schedule_management.todo.dto.request.TodoRequest;
import com.sparta.schedule_management.todo.dto.request.TodoUpdateRequest;
import com.sparta.schedule_management.todo.dto.response.TodoInfoResponse;
import com.sparta.schedule_management.todo.dto.response.TodoListResponse;
import com.sparta.schedule_management.todo.entity.Todo;
import com.sparta.schedule_management.todo.repository.TodoRepository;
import com.sparta.schedule_management.user.entity.User;
import com.sparta.schedule_management.user.repository.UserRepository;
import com.sparta.schedule_management.user.util.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;


    // 할일 작성
    @Transactional
    public void createTodo(TodoRequest request, User user) {
        User findUser = findUser(user); // findUser에 해당 사용자의 row(한줄)가 담긴다.
        Todo todo = new Todo(request.getTitle(), request.getContent(), findUser);
        todoRepository.save(todo);
    }

    // 할일 단건 조회
    public TodoInfoResponse getTodo(Long todoId) {
        Todo findTodoById = findTodoById(todoId);
        return new TodoInfoResponse(findTodoById.getUser().getUsername(), findTodoById.getTitle(), findTodoById.getContent(), findTodoById.getCreateAt());
    }


    // 할일 전체 조회
    public List<TodoListResponse> getTodos() {
        List<TodoListResponse> responses = todoRepository.findAll(Sort.by("createAt").descending()).stream().map(todo -> new TodoListResponse(todo.getUser().getUsername(), todo.getTitle(), todo.getCreateAt(), todo.getCompleted())).toList();
        // List<TodoListResponse> listResponses = todoRepository.findAll().stream().map(TodoListResponse::new).toList(); // todo.getUser().getUsername() 없을 경우 가능한 부분
        return responses;
    }

    // 할일 수정
    @Transactional
    public TodoInfoResponse updateTodo(Long todoId, TodoUpdateRequest request, User user) {
        // 해당 게시글의 사용자인지 일치여부
        Todo todo = findTodoById(todoId);
        if (!user.getId().equals(todo.getUser().getId())) {
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        // 비밀번호 일치여부
        if (!passwordEncoderUtil.passwordEncoder().matches(request.getPassword(), todo.getUser().getPassword())) {
            throw new RuntimeException("비밀번호 일치하지 않습니다"); // exception 찾아보기
        }
        todo.updateTodo(request.getTitle(), request.getContent()); // repository에 안가고->x (jpa가 업데이트를 체킹.!) 엔티티에 업데이트하는데 이게 되는게 더티체킹인가로 되는건인지?
        return new TodoInfoResponse(user.getUsername(), todo.getTitle(), todo.getContent(), todo.getCreateAt());

    }

    // 할일 완료
    @Transactional
    public TodoListResponse completeTodo(Long todoId, User user) {
        Todo todo = findTodoById(todoId);
        if (!user.getId().equals(todo.getUser().getId())) {
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        todo.completeTodo();
        return new TodoListResponse(todo.getUser().getUsername(), todo.getTitle(), todo.getCreateAt(), todo.getCompleted());
    }


    // 반복되는 코드 따로 빼둔 메소드들
    private User findUser(User user) {
        return userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다."));
    }

    private Todo findTodoById(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일 ID 입니다."));
    }

    // return new TodoResponse(user); // 이렇게 entity로 반환하면 안됨

}






