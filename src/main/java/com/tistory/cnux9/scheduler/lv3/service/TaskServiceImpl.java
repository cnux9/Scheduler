package com.tistory.cnux9.scheduler.lv3.service;

import com.tistory.cnux9.scheduler.lv3.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv3.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv3.entity.Task;
import com.tistory.cnux9.scheduler.lv3.repository.TaskRepository;
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
    public TaskResponseDto findTaskById(Long taskId) {
        return new TaskResponseDto(taskRepository.findTaskById(taskId));
    }

    @Override
    public List<TaskResponseDto> findAllTasks() {
        return taskRepository.findAllTasks();
    }

    @Override
    public List<TaskResponseDto> findTasksByEmail(String email) {
        return taskRepository.findTasksByEmail(email);
    }

    @Override
    public List<TaskResponseDto> findTasksByDate(LocalDate date) {
        return taskRepository.findTasksByDate(date);
    }

    @Override
    public List<TaskResponseDto> findTasksByEmailAndDate(String email, LocalDate date) {
        return taskRepository.findTasksByEmailAndDate(email, date);
    }

    @Transactional
    @Override
    public TaskResponseDto updateTask(Long taskId, TaskRequestDto dto) {
        Task task = taskRepository.findTaskById(taskId);
        if (!dto.getPassword().equals(task.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your password is wrong.");
        }

        // 덮어씌울 Task 객체 생성, 값 할당
        Task newTask = new Task(dto);
        newTask.setCreatedDateTime(task.getCreatedDateTime());
        newTask.setUpdatedDateTime(LocalDateTime.now());

        int updatedRowNum = taskRepository.updateTask(taskId, newTask);
        return new TaskResponseDto(taskRepository.findTaskById(taskId));
    }


    @Override
    public void deleteTask(Long taskId, String password) {
        Task task = taskRepository.findTaskById(taskId);
        if (!password.equals(task.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your password is wrong.");
        }
        taskRepository.deleteTask(taskId);
    }
}
