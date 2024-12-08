package com.tistory.cnux9.scheduler.lv4.service;

import com.tistory.cnux9.scheduler.lv4.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv4.dto.TaskSearchDto;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {
    TaskResponseDto saveTask(TaskRequestDto dto);

    TaskResponseDto findTaskById(Long taskId);

    List<TaskResponseDto> findTasks(TaskSearchDto dto);

    TaskResponseDto updateTask(Long taskId, TaskRequestDto dto);

    void deleteTask(Long taskId, TaskRequestDto dto);
}
