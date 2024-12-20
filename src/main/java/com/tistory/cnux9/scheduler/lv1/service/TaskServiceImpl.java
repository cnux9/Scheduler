package com.tistory.cnux9.scheduler.lv1.service;

import com.tistory.cnux9.scheduler.lv1.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv1.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv1.entity.Task;
import com.tistory.cnux9.scheduler.lv1.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponseDto saveTask(TaskRequestDto dto) {
        Task task = new Task(dto);

        // 작성일, 수정일 할당
        LocalDateTime now = LocalDateTime.now();
        task.setCreatedDateTime(now);
        task.setUpdatedDateTime(now);

        return taskRepository.saveTask(task);
    }

    @Override
    public TaskResponseDto findTaskById(Long id) {
        return new TaskResponseDto(taskRepository.findTaskById(id));
    }

    @Override
    public List<TaskResponseDto> findAllTasks() {
        return taskRepository.findAllTasks();
    }

    @Override
    public List<TaskResponseDto> findTasksByName(String name) {
        return taskRepository.findTasksByName(name);
    }

    @Override
    public List<TaskResponseDto> findTasksByDate(LocalDate date) {
        return taskRepository.findTasksByDate(date);
    }

    @Override
    public List<TaskResponseDto> findTasksByNameAndDate(String name, LocalDate date) {
        return taskRepository.findTasksByNameAndDate(name, date);
    }

    @Transactional
    @Override
    public TaskResponseDto updateTask(Long id, TaskRequestDto dto) {
        Task task = taskRepository.findTaskById(id);
        if (!dto.getPassword().equals(task.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your password is wrong.");
        }
        task.setUpdatedDateTime(LocalDateTime.now());
        int updatedRowNum = taskRepository.updateTask(id, task);
        return new TaskResponseDto(taskRepository.findTaskById(id));
    }
}
