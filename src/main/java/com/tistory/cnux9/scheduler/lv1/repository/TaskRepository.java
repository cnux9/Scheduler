package com.tistory.cnux9.scheduler.lv1.repository;

import com.tistory.cnux9.scheduler.lv1.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv1.entity.Task;

public interface TaskRepository {
    TaskResponseDto findTaskById(Long id);

    TaskResponseDto saveTask(Task task);
}
