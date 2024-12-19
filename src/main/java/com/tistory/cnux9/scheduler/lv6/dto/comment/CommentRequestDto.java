package com.tistory.cnux9.scheduler.lv6.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @JsonProperty("task_id")
    private Long taskId;
    @JsonProperty("user_id")
    private Long userId;
//    @JsonProperty("name")
//    private String userName;
    @NotNull
    private String password;
    @NotNull
    @Size(max = 200)
    private String content;
}
