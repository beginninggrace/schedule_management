package com.sparta.schedule_management.todo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.sparta.schedule_management.exception.NotExistsTodoException;
import com.sparta.schedule_management.todo.dto.request.TodoRequest;
import com.sparta.schedule_management.todo.dto.response.TodoInfoResponse;
import com.sparta.schedule_management.todo.entity.Todo;
import com.sparta.schedule_management.todo.repository.TodoRepository;
import com.sparta.schedule_management.user.entity.User;
import com.sparta.schedule_management.user.repository.UserRepository;
import com.sparta.schedule_management.user.util.PasswordEncoderUtil;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest {

    @Mock
    TodoRepository todoRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoderUtil passwordEncoderUtil;

    @InjectMocks
    TodoServiceImpl todoService;

    @Nested //클래스 안에 클래스
    @DisplayName("할일 작성 테스트")
    class TodoCreateTest {

        @Test
        void 할일작성_성공_테스트() {
            //given
            given(todoRepository.save(any(Todo.class))).willReturn(any(Todo.class));
            TodoRequest todoRequest = new TodoRequest("title", "content");
            User user = new User();
            ReflectionTestUtils.setField(user, "id", 1L);
            //when
            todoService.createTodo(todoRequest, user);
            //then
            then(todoRepository).should(times(1)).save(any(Todo.class));
        }

        @Test
        void 할일단건조회_성공_테스트() {
            //given
            LocalDateTime now = LocalDateTime.now();
            Todo todo = new Todo(1L, "title", "content", now, false, 1L);
            given(todoRepository.findById(anyLong())).willReturn(
                Optional.of(todo)); // findById 자체가 optional 반환
            User user = new User(1L, "username", "password");
            given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
            //when
            TodoInfoResponse todoInfoResponse = todoService.getTodo(1L);
            //then
            Assertions.assertEquals(user.getUsername(), todoInfoResponse.getUsername());
            Assertions.assertEquals(todo.getTitle(), todoInfoResponse.getTitle());
            Assertions.assertEquals(todo.getContent(), todoInfoResponse.getContent());
            Assertions.assertEquals(todo.getCreatedAt(), todoInfoResponse.getCreatedAt());
        }

        @Test
        void 투두가없는경우_할일단건조회_실패_테스트() {
            //given
            given(todoRepository.findById(anyLong())).willReturn(
                Optional.empty()); // findById 자체가 optional 반환
            //when
            String message = Assertions.assertThrows(NotExistsTodoException.class,
                () -> todoService.getTodo(1L)).getMessage();
            //then
            Assertions.assertEquals("해당 투두가 존재하지 않습니다", message);
//            Assertions.assertEquals(NotExistsTodoException.class"해당 투두가 존재하지 않습니다", message);
        }


    }

}