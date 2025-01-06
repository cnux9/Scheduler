package com.tistory.cnux9.scheduler.repository;

import com.tistory.cnux9.scheduler.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteCommentsByTaskTaskId(Long taskId);
}
