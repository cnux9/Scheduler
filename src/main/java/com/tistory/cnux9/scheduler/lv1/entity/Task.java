package com.tistory.cnux9.scheduler.lv1.entity;

import com.tistory.cnux9.scheduler.lv1.dto.TaskRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Slf4j
@Getter
//@AllArgsConstructor
public class Task {
    @Setter
    private Long id;
    private String content;
    private String name;
    private String password;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public Task(TaskRequestDto dto) {
        this.content = dto.getContent();
        this.name = dto.getName();
        this.password = dto.getPassword();
//        log.info("dto.getContent() = {}", dto.getContent());
//        log.info("dto.getName() = {}", dto.getName());
    }
}
