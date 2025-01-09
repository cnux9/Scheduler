package com.tistory.cnux9.scheduler.service;

import com.tistory.cnux9.scheduler.dto.task.TaskRequestDto;
import com.tistory.cnux9.scheduler.dto.task.TaskResponseDto;
import com.tistory.cnux9.scheduler.entity.Task;
import com.tistory.cnux9.scheduler.entity.User;
import com.tistory.cnux9.scheduler.exception.IdNotFoundException;
import com.tistory.cnux9.scheduler.repository.CommentRepository;
import com.tistory.cnux9.scheduler.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserService userService;
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;

    public TaskResponseDto save(TaskRequestDto dto) {
        Task task = new Task(dto.getContent());
        if (dto.getUserId() != null) {
            User foundUser = userService.findByIdOrElseThrow(dto.getUserId());
            task.setUser(foundUser);
        }
        return new TaskResponseDto(taskRepository.save(task));
    }

    public TaskResponseDto find(Long taskId) {
        Task task = findByIdOrElseThrow(taskId);
        return new TaskResponseDto(task);
    }

    public List<TaskResponseDto> findAll() {
        return taskRepository.findAll().stream().map(TaskResponseDto::new).toList();
    }

    @Transactional
    public TaskResponseDto update(Long taskId, TaskRequestDto dto) {
        Task task = findByIdOrElseThrow(taskId);

        task.setContent(dto.getContent());
        Long newUserId = dto.getUserId();
        if (newUserId != null) {
            if (task.getUser() == null || !task.getUser().getUserId().equals(newUserId)) {
                User foundUser = userService.findByIdOrElseThrow(dto.getUserId());
                task.setUser(foundUser);
            }
        }
        return new TaskResponseDto(task);
    }

    public Task findByIdOrElseThrow(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new IdNotFoundException(taskId));
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
