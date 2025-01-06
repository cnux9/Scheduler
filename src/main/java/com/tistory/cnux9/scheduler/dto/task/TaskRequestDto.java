package com.tistory.cnux9.scheduler.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskRequestDto {
    @JsonProperty("user_id")
    private final Long userId;
    @JsonProperty("name")
    private final String userName;
    @NotNull
    private final String password;
    @NotNull
    @Size(max = 200)
    private final String content;


}
