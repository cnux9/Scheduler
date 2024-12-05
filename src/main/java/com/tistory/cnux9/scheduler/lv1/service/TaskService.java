package com.tistory.cnux9.scheduler.lv1.service;

import com.tistory.cnux9.scheduler.lv1.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv1.dto.TaskResponseDto;
import org.springframework.stereotype.Service;

public interface TaskService {
    TaskResponseDto saveTask(TaskRequestDto dto);
}
