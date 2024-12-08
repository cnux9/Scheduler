package com.tistory.cnux9.scheduler.lv3.repository;

import com.tistory.cnux9.scheduler.lv3.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv3.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository {
    TaskResponseDto saveTask(Task task);

    Task findTaskById(Long TaskId);

    List<TaskResponseDto> findAllTasks();

    List<TaskResponseDto> findTasksByEmail(String email);

    List<TaskResponseDto> findTasksByDate(LocalDate date);

    List<TaskResponseDto> findTasksByEmailAndDate(String name, LocalDate date);

    int updateTask(Long id, Task task);

    void deleteTask(Long taskId);
}
