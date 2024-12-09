package com.tistory.cnux9.scheduler.lv4.repository;

import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv4.entity.Task;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskRepository {
    TaskResponseDto saveTask(Task task);

    Optional<Task> findTaskById(Long TaskId);

    Slice<TaskResponseDto> findTasks(Pageable Pageable, MultiValueMap<String, String> conditions);

    int updateTask(Long id, Task task);

    void deleteTask(Long taskId);
}
