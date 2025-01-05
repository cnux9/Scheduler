package com.tistory.cnux9.scheduler.dto.login;

import com.tistory.cnux9.scheduler.entity.User;

public class LoginResponseDto {
    private final Long userId;

    public LoginResponseDto(User user) {
        this.userId = user.getUserId();
    }
}
