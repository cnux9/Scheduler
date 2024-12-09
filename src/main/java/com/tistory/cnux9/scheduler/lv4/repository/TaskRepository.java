package com.tistory.cnux9.scheduler.lv4.repository;

import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv4.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

public interface TaskRepository {
    TaskResponseDto saveTask(Task task);

    Optional<Task> findTaskById(Long TaskId);

    Slice<TaskResponseDto> findTasks(Pageable Pageable, MultiValueMap<String, String> conditions);

    int updateTask(Long taskId, Task task);

    int updateUser(Long userId, String name);

    void deleteTask(Long taskId);
}
