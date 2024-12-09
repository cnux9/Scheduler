package com.tistory.cnux9.scheduler.lv4.service;

import com.tistory.cnux9.scheduler.lv4.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv4.entity.Task;
import com.tistory.cnux9.scheduler.lv4.repository.TaskRepository;
import com.tistory.cnux9.scheduler.lv4.resource.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

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
        Task task = taskRepository.findTaskById(taskId).orElseThrow(() -> new ResourceNotFoundException(taskId));
        return new TaskResponseDto(task);
    }

    @Override
    public List<TaskResponseDto> findTasks(MultiValueMap<String, Object> conditions) {
        return taskRepository.findTasks(conditions);
    }

    @Transactional
    @Override
    public TaskResponseDto updateTask(Long taskId, TaskRequestDto dto) {
        Task task = taskRepository.findTaskById(taskId).orElseThrow(() -> new ResourceNotFoundException(taskId));
        if (!dto.getPassword().equals(task.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your password is wrong.");
        }

        // 덮어씌울 Task 객체 생성, 값 할당
        Task newTask = new Task(dto);
        newTask.setCreatedDateTime(task.getCreatedDateTime());
        newTask.setUpdatedDateTime(LocalDateTime.now());

        int updatedRowNum = taskRepository.updateTask(taskId, newTask);
//        if (updatedRowNum == 0)
        return new TaskResponseDto(newTask);
    }

    @Override
    public void deleteTask(Long taskId, String password) {
        Task task = taskRepository.findTaskById(taskId).orElseThrow(() -> new ResourceNotFoundException(taskId));
        if (!password.equals(task.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your password is wrong.");
        }
        taskRepository.deleteTask(taskId);
    }


}
