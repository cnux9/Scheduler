package com.tistory.cnux9.scheduler.lv4.entity;

import com.tistory.cnux9.scheduler.lv4.dto.TaskRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Task {
    private Long taskId;
    private Long userId;
    private String userName;
    private String email;
    private String password;
    private String content;
    @Setter
    private LocalDateTime createdDateTime;
    @Setter
    private LocalDateTime updatedDateTime;

    public Task(TaskRequestDto dto) {
        this.userId = dto.getUserId();
        this.password = dto.getPassword();
        this.content = dto.getContent();
    }
}