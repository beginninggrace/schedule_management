package com.sparta.schedule_management.todo.service;

import com.sparta.schedule_management.exception.NotExistsTodoException;
import com.sparta.schedule_management.exception.NotExistsUserException;
import com.sparta.schedule_management.exception.NotMatchedPasswordException;
import com.sparta.schedule_management.exception.NotMatchedUserException;
import com.sparta.schedule_management.todo.dto.request.TodoRequest;
import com.sparta.schedule_management.todo.dto.request.TodoUpdateRequest;
import com.sparta.schedule_management.todo.dto.response.TodoInfoResponse;
import com.sparta.schedule_management.todo.dto.response.TodoListResponse;
import com.sparta.schedule_management.todo.entity.Todo;
import com.sparta.schedule_management.todo.repository.TodoRepository;
import com.sparta.schedule_management.user.entity.User;
import com.sparta.schedule_management.user.repository.UserRepository;
import com.sparta.schedule_management.user.util.PasswordEncoderUtil;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoderUtil;


    @Override
    // 할일 작성
    @Transactional
    public void createTodo(TodoRequest request, User user) {
        Todo todo = new Todo(
            request.getTitle(),
            request.getContent(),
            user.getId());
        todoRepository.save(todo);
    }

    @Override
    // 할일 단건 조회
    public TodoInfoResponse getTodo(Long todoId) {
        Todo todo = findTodoById(todoId);
        // username 필요함
        // user 필요
        // userid 필요
        // (userid는 todo에 있다)
        User user = userRepository.findById(todo.getUserId())
            .orElseThrow(NotExistsUserException::new);
        return new TodoInfoResponse(
            user.getUsername(),
            todo.getTitle(),
            todo.getContent(),
            todo.getCreatedAt());
    }

    @Override
    // 할일 전체 조회
    public List<TodoListResponse> getTodos() {
        List<TodoListResponse> responses = todoRepository.findAll(Sort.by("createdAt").descending())
            .stream().map(
                (Todo todo) -> {
                    User user = userRepository.findById(todo.getUserId())
                        .orElseThrow(NotExistsUserException::new);
                    return new TodoListResponse(user.getUsername(), todo.getTitle(),
                        todo.getCreatedAt(), todo.getCompleted());
                }).toList();
        return responses;
    }

    @Override
    // 할일 수정
    @Transactional
    public TodoInfoResponse updateTodo(Long todoId, TodoUpdateRequest request, User user) {
        // 해당 게시글의 사용자인지 일치여부
        Todo todo = findTodoById(todoId);
        if (!todo.getUserId().equals(user.getId())) {
            throw new NotMatchedUserException();
        }
        // 비밀번호 일치여부
        if (!passwordEncoderUtil.passwordEncoder()
            .matches(request.getPassword(), user.getPassword())) {
            throw new NotMatchedPasswordException();
        }
        todo.updateTodo(request.getTitle(),
            request.getContent());
        return new TodoInfoResponse(user.getUsername(), todo.getTitle(), todo.getContent(),
            todo.getCreatedAt());

    }

    @Override
    // 할일 완료
    @Transactional
    public TodoListResponse completeTodo(Long todoId, User user) {
        Todo todo = findTodoById(todoId);
        User user1 = userRepository.findById(todo.getUserId())
            .orElseThrow(NotExistsUserException::new);
        if (!user.getId().equals(user1.getId())) {
            throw new NotMatchedUserException();
        }
        todo.completeTodo();
        return new TodoListResponse(user1.getUsername(), todo.getTitle(),
            todo.getCreatedAt(), todo.getCompleted());
    }


    private Todo findTodoById(Long todoId) {
        return todoRepository.findById(todoId)
            .orElseThrow(NotExistsTodoException::new);
    }

}






