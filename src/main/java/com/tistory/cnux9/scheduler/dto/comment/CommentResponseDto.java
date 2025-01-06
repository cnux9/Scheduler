package com.tistory.cnux9.scheduler.dto.comment;

import com.tistory.cnux9.scheduler.entity.Comment;
import com.tistory.cnux9.scheduler.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private final Long commentId;
    private final Long taskId;
    private final Long userId;
    private final String userName;
    private final String email;
    private final String content;
    private final LocalDateTime createdDateTime;
    private final LocalDateTime updatedDateTime;

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
