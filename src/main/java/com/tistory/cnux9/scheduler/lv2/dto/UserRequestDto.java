package com.tistory.cnux9.scheduler.lv2.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @JsonProperty("user_name")
    private String userName;

    @NotNull
    @Email
    private String email;

    private String password;
}
