package com.tistory.cnux9.scheduler.lv3.dto;

import com.tistory.cnux9.scheduler.lv3.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskResponseDto {
    private Long taskId;
    private Long userId;
    private String email;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public TaskResponseDto(Task task) {
        this.taskId = task.getTaskId();
        this.userId = task.getUserId();
        this.email = task.getEmail();
        this.content = task.getContent();
        this.createdDateTime = task.getCreatedDateTime();
        this.updatedDateTime = task.getUpdatedDateTime();
    }
}
