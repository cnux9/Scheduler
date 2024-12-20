package com.tistory.cnux9.scheduler.lv1.entity;

import com.tistory.cnux9.scheduler.lv1.dto.TaskRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Task {
    private Long id;
    private String content;
    private String name;
    private String password;
    @Setter
    private LocalDateTime createdDateTime;
    @Setter
    private LocalDateTime updatedDateTime;

    public Task(TaskRequestDto dto) {
        this.content = dto.getContent();
        this.name = dto.getName();
        this.password = dto.getPassword();
    }
}
