package com.tistory.cnux9.scheduler.lv1.repository;

import com.tistory.cnux9.scheduler.lv1.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv1.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv1.entity.Task;

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

    String getPasswordById(Long id);
}
