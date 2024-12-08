package com.tistory.cnux9.scheduler.lv3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TaskRequestDto {
    @JsonProperty("user_id")
    private Long userId;
    private String password;
    private String content;
}
