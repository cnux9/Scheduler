package com.tistory.cnux9.scheduler.lv6.dto.login;

import com.tistory.cnux9.scheduler.lv6.entity.User;

public class LoginResponseDto {
    private final Long userId;

    public LoginResponseDto(User user) {
        this.userId = user.getUserId();
    }
}
