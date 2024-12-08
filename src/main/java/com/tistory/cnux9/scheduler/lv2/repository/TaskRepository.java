package com.tistory.cnux9.scheduler.lv2.repository;

import com.tistory.cnux9.scheduler.lv2.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv2.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository {
    TaskResponseDto saveTask(Task task);

    Task findTaskById(Long id);

    List<TaskResponseDto> findAllTasks();

    List<TaskResponseDto> findTasksByName(String name);

    List<TaskResponseDto> findTasksByDate(LocalDate date);

    List<TaskResponseDto> findTasksByNameAndDate(String name, LocalDate date);

    int updateTask(Long id, Task task);

    void deleteTask(Long id);
}
