package com.sparta.schedule_management.comment.controller;

import com.sparta.schedule_management.comment.dto.request.CommentRequest;
import com.sparta.schedule_management.comment.dto.request.UpdateCommentRequest;
import com.sparta.schedule_management.comment.dto.response.CommentResponse;
import com.sparta.schedule_management.comment.service.CommentServiceImpl;
import com.sparta.schedule_management.user.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentServiceImpl commentServiceImpl;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<Void> createComment(
        @Valid @RequestBody CommentRequest request,
        @PathVariable Long todoId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentServiceImpl.createComment(request, todoId, userDetails.getUser().getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
        @Valid @RequestBody UpdateCommentRequest request,
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentServiceImpl.updateComment(request, commentId, userDetails.getUser().getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
        @PathVariable Long commentId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentServiceImpl.deleteComment(commentId, userDetails.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
