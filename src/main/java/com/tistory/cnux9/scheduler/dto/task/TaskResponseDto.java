package com.tistory.cnux9.scheduler.dto.task;

import com.tistory.cnux9.scheduler.entity.Task;
import com.tistory.cnux9.scheduler.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TaskResponseDto {
    private final Long taskId;
    private final Long userId;
    private final String userName;
    private final String email;
    private final String content;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime updatedDateTime;

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
        } else {
            this.userId = null;
            this.userName = null;
            this.email = null;
        }
    }
}
