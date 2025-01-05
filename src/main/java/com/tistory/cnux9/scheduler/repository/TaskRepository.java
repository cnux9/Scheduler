package com.tistory.cnux9.scheduler.repository;

import com.tistory.cnux9.scheduler.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface TaskRepository extends JpaRepository<Task, Long> {
    default Task findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id does not exist : " + id));
    }
}
