package com.tistory.cnux9.scheduler.lv4.service;

import com.tistory.cnux9.scheduler.lv4.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface TaskService {
    TaskResponseDto saveTask(TaskRequestDto dto);

    TaskResponseDto findTaskById(Long taskId);

    List<TaskResponseDto> findTasks(MultiValueMap<String, String> conditions);

    TaskResponseDto updateTask(Long taskId, String name, TaskRequestDto dto);

    void deleteTask(Long taskId, String password);
}
