package com.tistory.cnux9.scheduler.lv2.entity;

import com.tistory.cnux9.scheduler.lv2.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Setter
    @Column(name = "user_name")
    private String userName;

    private String password;

    @Setter
    private String email;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDateTime;


    public User(UserRequestDto dto) {
        this.userName = dto.getUserName();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
    }
}
