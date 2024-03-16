package com.sparta.schedule_management.comment.repository;

import com.sparta.schedule_management.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
