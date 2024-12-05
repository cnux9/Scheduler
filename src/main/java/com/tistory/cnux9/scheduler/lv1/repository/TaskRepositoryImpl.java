package com.tistory.cnux9.scheduler.lv1.repository;

import com.tistory.cnux9.scheduler.lv1.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class TaskRepositoryImpl implements TaskRepository{
    // 일단 외부 데이터베이스 나중에 구현
    private final Map<Long, Task> taskMap = new HashMap<>();

    @Override
    public Task saveTask(Task task) {
        Long taskId = taskMap.isEmpty() ? 1 : Collections.max(taskMap.keySet())+1;
        task.setId(taskId);
//        log.info("task.getContent() = {}", task.getContent());
//        log.info("task.getName() = {}", task.getName());
        taskMap.put(taskId, task);

        return task;
    }
}
