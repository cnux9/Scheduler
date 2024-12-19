package com.tistory.cnux9.scheduler.lv6.service;

import com.tistory.cnux9.scheduler.lv6.dto.task.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv6.dto.task.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv6.entity.Comment;
import com.tistory.cnux9.scheduler.lv6.entity.Task;
import com.tistory.cnux9.scheduler.lv6.entity.User;
import com.tistory.cnux9.scheduler.lv6.repository.CommentRepository;
import com.tistory.cnux9.scheduler.lv6.repository.TaskRepository;
import com.tistory.cnux9.scheduler.lv6.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    public TaskResponseDto save(TaskRequestDto dto) {
        Task task = new Task(dto.getContent());
        if (dto.getUserId() != null) {
            User foundUser = userRepository.findByIdOrElseThrow(dto.getUserId());
            task.setUser(foundUser);
        }
        return new TaskResponseDto(taskRepository.save(task));
    }

    public TaskResponseDto find(Long taskId) {
        Task task = taskRepository.findByIdOrElseThrow(taskId);
        return new TaskResponseDto(task);
    }

    public List<TaskResponseDto> findAll() {
        return taskRepository.findAll().stream().map(TaskResponseDto::new).toList();
    }

    @Transactional
    public TaskResponseDto update(Long taskId, TaskRequestDto dto) {
        Task task = taskRepository.findByIdOrElseThrow(taskId);
        if (!dto.getPassword().equals(task.getUser().getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your password is wrong.");
        }

        task.setContent(dto.getContent());
        Long newUserId = dto.getUserId();
        if (newUserId!=null) {
            if (task.getUser() == null || !task.getUser().getUserId().equals(newUserId) ) {
                User foundUser = userRepository.findByIdOrElseThrow(dto.getUserId());
                task.setUser(foundUser);
            }
        }
        return new TaskResponseDto(task);
    }


//    @Transactional
    public void delete(Long taskId) {
//        Task foundTask = taskRepository.findByIdOrElseThrow(taskId);
//        commentRepository.deleteCommentsByTaskTaskId(taskId);
//
//        taskRepository.delete(foundTask);

        taskRepository.deleteById(taskId);
    }
}
