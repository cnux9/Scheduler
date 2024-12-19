package com.tistory.cnux9.scheduler.lv6.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {
    @NotNull
    @Size(max = 200)
    private String content;
}
