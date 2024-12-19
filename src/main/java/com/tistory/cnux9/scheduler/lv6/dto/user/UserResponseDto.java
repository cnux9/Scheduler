package com.tistory.cnux9.scheduler.lv6.dto.user;

import com.tistory.cnux9.scheduler.lv6.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long userId;
    private String userName;
    private String email;
    private LocalDateTime createdDateTime;
    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.createdDateTime = user.getCreatedDateTime();
    }
}