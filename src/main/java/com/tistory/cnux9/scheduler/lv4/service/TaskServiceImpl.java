package com.tistory.cnux9.scheduler.lv4.service;

import com.tistory.cnux9.scheduler.lv4.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv4.dto.TaskSearchDto;
import com.tistory.cnux9.scheduler.lv4.entity.Task;
import com.tistory.cnux9.scheduler.lv4.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        Task task = taskRepository.findTaskById(taskId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist task_id = " + taskId));
        return new TaskResponseDto(task);
    }

    @Override
    public List<TaskResponseDto> findTasks(TaskSearchDto dto) {
        String email = dto.getEmail();
        LocalDate date = dto.getDate();

        List<TaskResponseDto> taskList;
        if (email == null) {
            if (date == null) {
                taskList = taskRepository.findAllTasks();
            } else {
                taskList = taskRepository.findTasksByDate(date);
            }
        } else {
            if (date == null) {
                taskList = taskRepository.findTasksByEmail(email);
            } else {
                taskList = taskRepository.findTasksByEmailAndDate(email, date);
            }
        }
        return taskList;
    }

    @Transactional
    @Override
    public TaskResponseDto updateTask(Long taskId, TaskRequestDto dto) {
        Task task = taskRepository.findTaskById(taskId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist task_id = " + taskId));
        if (!dto.getPassword().equals(task.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your password is wrong.");
        }

        // 덮어씌울 Task 객체 생성, 값 할당
        Task newTask = new Task(dto);
        newTask.setCreatedDateTime(task.getCreatedDateTime());
        newTask.setUpdatedDateTime(LocalDateTime.now());

        int updatedRowNum = taskRepository.updateTask(taskId, newTask);
//        if (updatedRowNum == 0)
        return new TaskResponseDto(taskRepository.findTaskById(taskId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist task_id = " + taskId)));
    }

    @Override
    public void deleteTask(Long taskId, TaskRequestDto dto) {
        Task task = taskRepository.findTaskById(taskId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist task_id = " + taskId));
        if (!dto.getPassword().equals(task.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your password is wrong.");
        }
        taskRepository.deleteTask(taskId);
    }
}
