package com.schedulemanagerdevelop.repository;

import com.schedulemanagerdevelop.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}