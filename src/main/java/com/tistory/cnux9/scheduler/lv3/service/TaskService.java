package com.tistory.cnux9.scheduler.lv3.service;

import com.tistory.cnux9.scheduler.lv3.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv3.dto.TaskResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    TaskResponseDto saveTask(TaskRequestDto dto);

    TaskResponseDto findTaskById(Long taskId);

    List<TaskResponseDto> findAllTasks();

    List<TaskResponseDto> findTasksByEmail(String email);

    List<TaskResponseDto> findTasksByDate(LocalDate date);

    List<TaskResponseDto> findTasksByEmailAndDate(String email, LocalDate date);

    TaskResponseDto updateTask(Long taskId, TaskRequestDto dto);

    void deleteTask(Long taskId, String password);
}
