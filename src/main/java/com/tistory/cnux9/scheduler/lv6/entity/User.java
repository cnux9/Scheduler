package com.tistory.cnux9.scheduler.lv6.entity;

import com.tistory.cnux9.scheduler.lv6.config.PasswordEncoder;
import com.tistory.cnux9.scheduler.lv6.dto.user.UserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class User extends CreatedEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Setter
    @Column(name = "user_name")
    private String userName;

    private String password;

    @Setter
    @Column(unique = true)
    private String email;


    public User(UserRequestDto dto) {
        this.userName = dto.getUserName();
        this.password = PasswordEncoder.encode(dto.getPassword());
        this.email = dto.getEmail();
    }
}
