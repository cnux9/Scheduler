package com.tistory.cnux9.scheduler.lv1.repository;

import com.tistory.cnux9.scheduler.lv1.entity.Task;


public interface TaskRepository {
    Task saveTask(Task task);
}
