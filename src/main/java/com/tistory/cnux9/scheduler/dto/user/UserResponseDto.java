package com.tistory.cnux9.scheduler.dto.user;

import com.tistory.cnux9.scheduler.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private final Long userId;
    private final String userName;
    private final String email;
    private final LocalDateTime createdDateTime;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.createdDateTime = user.getCreatedDateTime();
    }
}
