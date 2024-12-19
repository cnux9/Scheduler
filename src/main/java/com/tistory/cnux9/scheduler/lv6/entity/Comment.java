package com.tistory.cnux9.scheduler.lv6.entity;

import com.tistory.cnux9.scheduler.lv6.dto.comment.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends CreatedEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Setter
    private String content;

    @Setter
    @LastModifiedDate
    private LocalDateTime updatedDateTime;

    public Comment(String content) {
        this.content = content;
    }
}
