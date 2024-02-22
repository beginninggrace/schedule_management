package com.sparta.schedule_management.todo.service;

import com.sparta.schedule_management.todo.dto.response.TodoInfoResponse;
import com.sparta.schedule_management.todo.dto.request.TodoRequest;
import com.sparta.schedule_management.todo.dto.response.TodoListResponse;
import com.sparta.schedule_management.todo.entity.Todo;
import com.sparta.schedule_management.todo.repository.TodoRepository;
import com.sparta.schedule_management.user.entity.User;
import com.sparta.schedule_management.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    // 할일 작성
    @Transactional
    public void createTodo(TodoRequest request, User user) {
        User findUser = findUser(user); // findUser에 해당 사용자의 row(한줄)가 담긴다.
        Todo todo = new Todo(request.getTitle(), request.getContent(), findUser); // 파라미터에 그냥 request로 하는 것도 있는데 이유는?
        todoRepository.save(todo);
    }

    // 할일 단건 조회
    public TodoInfoResponse getTodo(Long todoId) {
        Todo findTodoById = findTodoById(todoId);
        return new TodoInfoResponse(findTodoById.getTitle(), findTodoById.getContent(), findTodoById.getCreateAt(), findTodoById.getUser().getUsername());
    }


    // 할일 전체 조회
    public List<TodoListResponse> getTodos() {
        List<TodoListResponse> responses = todoRepository.findAll(Sort.by("createAt").descending()).stream().map(todo -> new TodoListResponse(todo.getUser().getUsername(), todo.getTitle(), todo.getCreateAt(), todo.getCompleted())).toList();
        // List<TodoListResponse> listResponses = todoRepository.findAll().stream().map(TodoListResponse::new).toList(); // todo.getUser().getUsername() 없을 경우 가능한 부분
        return responses;
    }

//    // 할일 수정
//    @Transactional
//    public TodoResponse updateTodo(Long todoId, TodoRequest request, User user) {
//        Todo todo = getUserTodo(todoId, user);
//
//        todo.updateTodo(request.getTitle(), request.getContent());
//
//        // return new TodoResponse(todo); // ★ entity로 반환해줘도 되나? 이렇게 하면 안된댓지?
//        return null; //new TodoResponse(); // 들어온 id(seq)값이 맞는 id인지 검증하는걸 여기 서비스로직 내에서 이루어지면 TodoResponse 항목 중에 id값 제회한 검증하고 싶은 요소들을 return하면 되는가?
//    }


    // 반복되는 코드 따로 빼둔 메소드들
    private User findUser(User user) {
        return userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다."));
    }

    private Todo findTodoById(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일 ID 입니다."));
    }
}

//    public Todo getUserTodo(Long todoId, User user) {
////        Todo todo = getTodo(todoId);
////
////        if(!user.getId().equals(todo.getUser().getId())) {
////            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
////        }
////        return todo;
//        return null;
//    }
//
//}
