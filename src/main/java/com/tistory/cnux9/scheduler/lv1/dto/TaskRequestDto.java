package com.tistory.cnux9.scheduler.lv1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TaskRequestDto {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("name")
    private String userName;

    @NotNull
    private String password;

    @NotNull
    @Size(max = 200)
    private String content;
}
