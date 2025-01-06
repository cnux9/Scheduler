package com.tistory.cnux9.scheduler.service;

import com.tistory.cnux9.scheduler.dto.comment.CommentUpdateRequestDto;
import com.tistory.cnux9.scheduler.entity.Task;
import com.tistory.cnux9.scheduler.exception.IdNotFoundException;
import com.tistory.cnux9.scheduler.exception.ResourceNotFoundException;
import com.tistory.cnux9.scheduler.dto.comment.CommentRequestDto;
import com.tistory.cnux9.scheduler.dto.comment.CommentResponseDto;
import com.tistory.cnux9.scheduler.entity.Comment;
import com.tistory.cnux9.scheduler.entity.User;
import com.tistory.cnux9.scheduler.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskService taskService;
    private final UserService userService;


    public CommentResponseDto save(CommentRequestDto dto) {
        Comment comment = new Comment(dto.getContent());

        Task foundTask = taskService.findByIdOrElseThrow(dto.getTaskId());
        comment.setTask(foundTask);

        User foundUser = userService.findByIdOrElseThrow(dto.getUserId());
        comment.setUser(foundUser);

        return new CommentResponseDto(commentRepository.save(comment));
    }

    public CommentResponseDto find(Long commentId) {
        Comment foundComment = findByIdOrElseThrow(commentId);
        return new CommentResponseDto(foundComment);
    }

    public List<CommentResponseDto> findAll() {
        List<Comment> foundCommentList = commentRepository.findAll();
        return foundCommentList.stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
    public CommentResponseDto update(Long commentId, CommentUpdateRequestDto dto) {
        Comment foundComment = findByIdOrElseThrow(commentId);
        foundComment.setContent(dto.getContent());
        return new CommentResponseDto(foundComment);
    }

    public void delete(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    private Comment findByIdOrElseThrow(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new IdNotFoundException(commentId));
    }
}
