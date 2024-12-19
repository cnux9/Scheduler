package com.tistory.cnux9.scheduler.lv6.service;

import com.tistory.cnux9.scheduler.lv6.dto.comment.CommentUpdateRequestDto;
import com.tistory.cnux9.scheduler.lv6.entity.Task;
import com.tistory.cnux9.scheduler.lv6.repository.TaskRepository;
import com.tistory.cnux9.scheduler.lv6.dto.comment.CommentRequestDto;
import com.tistory.cnux9.scheduler.lv6.dto.comment.CommentResponseDto;
import com.tistory.cnux9.scheduler.lv6.entity.Comment;
import com.tistory.cnux9.scheduler.lv6.entity.User;
import com.tistory.cnux9.scheduler.lv6.repository.CommentRepository;
import com.tistory.cnux9.scheduler.lv6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    public CommentResponseDto save(CommentRequestDto dto) {
        Comment comment = new Comment(dto.getContent());

        Task foundTask = taskRepository.findByIdOrElseThrow(dto.getTaskId());
        comment.setTask(foundTask);

        User foundUser = userRepository.findByIdOrElseThrow(dto.getUserId());
        comment.setUser(foundUser);

        return new CommentResponseDto(commentRepository.save(comment));
    }

    public CommentResponseDto find(Long commentId) {
        Comment foundComment = commentRepository.findByIdOrElseThrow(commentId);
        return new CommentResponseDto(foundComment);
    }

    public List<CommentResponseDto> findAll() {
        List<Comment> foundCommentList = commentRepository.findAll();
        return foundCommentList.stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
    public CommentResponseDto update(Long commentId, CommentUpdateRequestDto dto) {
        Comment foundComment = commentRepository.findByIdOrElseThrow(commentId);
        foundComment.setContent(dto.getContent());
        return new CommentResponseDto(foundComment);
    }

    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
