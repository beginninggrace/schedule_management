package com.sparta.schedule_management.todo.repository;

import static com.sparta.schedule_management.todo.entity.QTodo.todo;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.schedule_management.todo.entity.QTodo;
import com.sparta.schedule_management.todo.entity.Todo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;


@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoQueryRepository {

    private final JPAQueryFactory queryFactory;


    public List<Todo> searchTodoKeyword(String keyword) {
//        JPAQuery<Todo> query = queryFactory.selectFrom(todo);
        QTodo todo = QTodo.todo;

        return queryFactory.selectFrom(todo)
            .where(todo.title.contains(keyword))
            .orderBy(todo.createdAt.desc())
            .fetch();
    }

    public List<Todo> findPagingAll(Pageable pageable) {
        JPAQuery<Todo> query = queryFactory.select(todo)
            .from(todo)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize());
        List<Todo> todoList = query.fetch();
        JPAQuery<Long> count = queryFactory.select(todo.count())
            .from(todo);
        return PageableExecutionUtils.getPage(todoList, pageable, count::fetchOne).toList();
    }
}
