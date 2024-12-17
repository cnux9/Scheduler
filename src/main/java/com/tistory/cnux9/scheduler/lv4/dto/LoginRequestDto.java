package com.tistory.cnux9.scheduler.lv4.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {
    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;
}
