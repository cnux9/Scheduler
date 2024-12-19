package com.tistory.cnux9.scheduler.lv6.dto.comment;

import com.tistory.cnux9.scheduler.lv6.entity.Comment;
import com.tistory.cnux9.scheduler.lv6.entity.Task;
import com.tistory.cnux9.scheduler.lv6.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private Long taskId;
    private Long userId;
    private String userName;
    private String email;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.taskId = comment.getTask().getTaskId();
        this.userId = comment.getUser().getUserId();
        this.content = comment.getContent();
        this.createdDateTime = comment.getCreatedDateTime();
        this.updatedDateTime = comment.getUpdatedDateTime();

        User user = comment.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.userName = user.getUserName();
            this.email = user.getEmail();
        }
    }
}
