package com.tistory.cnux9.scheduler.repository;

import com.tistory.cnux9.scheduler.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
