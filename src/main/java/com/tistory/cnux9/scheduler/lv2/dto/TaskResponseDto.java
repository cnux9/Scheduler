package com.tistory.cnux9.scheduler.lv2.dto;

import com.tistory.cnux9.scheduler.lv2.entity.Task;
import com.tistory.cnux9.scheduler.lv2.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskResponseDto {
    private Long taskId;
    private Long userId;
    private String userName;
    private String email;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public TaskResponseDto(Task task) {
        this.taskId = task.getTaskId();
        this.content = task.getContent();
        this.createdDateTime = task.getCreatedDateTime();
        this.updatedDateTime = task.getUpdatedDateTime();

        User user = task.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.userName = user.getUserName();
            this.email = user.getEmail();
        }
    }
}
