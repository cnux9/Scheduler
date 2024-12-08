package com.tistory.cnux9.scheduler.lv4.repository;

import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv4.entity.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    TaskResponseDto saveTask(Task task);

    Optional<Task> findTaskById(Long TaskId);

    List<TaskResponseDto> findAllTasks();

    List<TaskResponseDto> findTasksByEmail(String email);

    List<TaskResponseDto> findTasksByDate(LocalDate date);

    List<TaskResponseDto> findTasksByEmailAndDate(String name, LocalDate date);

    int updateTask(Long id, Task task);

    void deleteTask(Long taskId);
}
