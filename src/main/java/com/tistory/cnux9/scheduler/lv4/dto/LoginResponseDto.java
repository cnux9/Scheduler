package com.tistory.cnux9.scheduler.lv4.dto;

import com.tistory.cnux9.scheduler.lv4.entity.User;

public class LoginResponseDto {
    private final Long userId;

    public LoginResponseDto(User user) {
        this.userId = user.getUserId();
    }
}
