package com.tistory.cnux9.scheduler.lv4.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
@Getter
public class TaskRequestDto {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("name")
    private String userName;
    @NotNull
    private String password;
    @NotNull
    private String content;
}
