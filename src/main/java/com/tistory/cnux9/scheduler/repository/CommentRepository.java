package com.tistory.cnux9.scheduler.repository;

import com.tistory.cnux9.scheduler.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteCommentsByTaskTaskId(Long taskId);
}
