package com.tistory.cnux9.scheduler.lv1.dto;

import com.tistory.cnux9.scheduler.lv1.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskResponseDto {
    private Long id;
    private String content;
    private String name;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public TaskResponseDto(Task task) {
        this.id = task.getId();
        this.content = task.getContent();
        this.name = task.getUserName();
        this.createdDateTime = task.getCreatedDateTime();
        this.updatedDateTime = task.getUpdatedDateTime();
    }
}
