package com.sparta.schedule_management.comment.service;

import com.sparta.schedule_management.comment.dto.request.CommentRequest;
import com.sparta.schedule_management.comment.dto.request.UpdateCommentRequest;
import com.sparta.schedule_management.user.entity.User;

public interface CommentService {

    // 댓글 작성
    void createComment(CommentRequest request, Long userId, Long todoId);

    // 댓글 수정
    void updateComment(UpdateCommentRequest request, Long commentId, Long userId);

    // 댓글 삭제
    void deleteComment(Long commentId, User user);
}
