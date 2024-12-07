package com.tistory.cnux9.scheduler.lv1.service;

import com.tistory.cnux9.scheduler.lv1.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv1.dto.TaskResponseDto;

public interface TaskService {
    TaskResponseDto saveTask(TaskRequestDto dto);

    TaskResponseDto findTaskById(Long id);
}
