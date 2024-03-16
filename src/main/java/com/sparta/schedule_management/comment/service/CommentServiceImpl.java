package com.sparta.schedule_management.comment.service;

import com.sparta.schedule_management.comment.dto.request.CommentRequest;
import com.sparta.schedule_management.comment.dto.request.UpdateCommentRequest;
import com.sparta.schedule_management.comment.entity.Comment;
import com.sparta.schedule_management.comment.repository.CommentRepository;
import com.sparta.schedule_management.exception.NotExistsCommentException;
import com.sparta.schedule_management.exception.NotExistsTodoException;
import com.sparta.schedule_management.todo.entity.Todo;
import com.sparta.schedule_management.todo.repository.TodoRepository;
import com.sparta.schedule_management.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;


    // 댓글 작성
    @Override
    @Transactional
    public void createComment(
        CommentRequest request,
        Long userId,
        Long todoId) {
        Todo todo = findTodo(todoId);
        commentRepository.save(new Comment(userId, todo.getId(), request));
    }

    // 댓글 수정
    @Override
    @Transactional
    public void updateComment(
        UpdateCommentRequest request,
        Long commentId,
        Long userId) {
        Comment comment = findComment(commentId);
        comment.updateComment(request, userId);
    }

    // 댓글 삭제
    @Override
    @Transactional
    public void deleteComment(
        Long commentId,
        User user) {
        Comment comment = findComment(commentId);
        commentRepository.delete(comment);
    }

    private Todo findTodo(Long todoId) {
        return todoRepository.findById(todoId)
            .orElseThrow(NotExistsTodoException::new);
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(NotExistsCommentException::new);
    }
}
