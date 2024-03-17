package com.sparta.schedule_management.todo.repository;

import com.sparta.schedule_management.todo.entity.Todo;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TodoQueryRepository {
    public List<Todo> searchTodoKeyword(String keyword);

    public List<Todo> findPagingAll(Pageable pageable);

}
