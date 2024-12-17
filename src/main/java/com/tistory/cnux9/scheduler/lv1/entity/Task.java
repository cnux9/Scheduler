package com.tistory.cnux9.scheduler.lv1.entity;

import com.tistory.cnux9.scheduler.lv1.dto.TaskRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String password;

    @Setter
    private String content;

    @Setter
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDateTime;

    @Setter
    @LastModifiedDate
    private LocalDateTime updatedDateTime;

    public Task(TaskRequestDto dto) {
        this.password = dto.getPassword();
        this.content = dto.getContent();
    }
}