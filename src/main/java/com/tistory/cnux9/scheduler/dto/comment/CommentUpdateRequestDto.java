package com.tistory.cnux9.scheduler.dto.comment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {
    @NotNull
    @Size(max = 200)
    private String content;
}
