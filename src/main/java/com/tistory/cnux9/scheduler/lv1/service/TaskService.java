package com.tistory.cnux9.scheduler.lv1.service;

import com.tistory.cnux9.scheduler.lv1.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv1.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv1.entity.Task;
import com.tistory.cnux9.scheduler.lv1.entity.User;
import com.tistory.cnux9.scheduler.lv1.repository.TaskRepository;
import com.tistory.cnux9.scheduler.lv1.repository.UserRepository;
import com.tistory.cnux9.scheduler.lv1.resource.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskResponseDto saveTask(TaskRequestDto dto) {
        Task task = new Task(dto);
        if (dto.getUserId() != null) {
            User foundUser = userRepository.findByIdOrElseThrow(dto.getUserId());
            task.setUser(foundUser);
        }
        return new TaskResponseDto(taskRepository.save(task));
    }

    public TaskResponseDto findTaskById(Long taskId) {
        Task task = taskRepository.findByIdOrElseThrow(taskId);
        return new TaskResponseDto(task);
    }

    public List<TaskResponseDto> findTasks() {
        return taskRepository.findAll().stream().map(TaskResponseDto::new).toList();
    }

    public TaskResponseDto updateTask(Long taskId, TaskRequestDto dto) {
        Task task = taskRepository.findByIdOrElseThrow(taskId);
        if (!dto.getPassword().equals(task.getPassword())) {
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

    public void deleteTask(Long taskId, String password) {
        Task task = taskRepository.findByIdOrElseThrow(taskId);
        if (!password.equals(task.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your password is wrong.");
        }
        taskRepository.delete(task);
    }


}
