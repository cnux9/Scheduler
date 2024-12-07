package com.tistory.cnux9.scheduler.lv1.service;

import com.tistory.cnux9.scheduler.lv1.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv1.dto.TaskResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    TaskResponseDto saveTask(TaskRequestDto dto);

    TaskResponseDto findTaskById(Long id);

    List<TaskResponseDto> findAllTasks();

    List<TaskResponseDto> findTasksByName(String name);

    List<TaskResponseDto> findTasksByDate(LocalDate date);

    List<TaskResponseDto> findTasksByNameAndDate(String name, LocalDate date);

    TaskResponseDto updateTask(Long id, TaskRequestDto dto);
}
