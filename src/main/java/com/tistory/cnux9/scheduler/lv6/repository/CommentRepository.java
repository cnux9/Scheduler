package com.tistory.cnux9.scheduler.lv6.repository;

import com.tistory.cnux9.scheduler.lv6.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id does not exist : " + id));
    }

    void deleteCommentsByTaskTaskId(Long taskId);
}
