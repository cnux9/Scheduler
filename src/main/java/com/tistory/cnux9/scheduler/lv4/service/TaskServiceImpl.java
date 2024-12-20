package com.tistory.cnux9.scheduler.lv4.service;

import com.tistory.cnux9.scheduler.lv4.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv4.entity.Task;
import com.tistory.cnux9.scheduler.lv4.repository.TaskRepository;
import com.tistory.cnux9.scheduler.lv4.resource.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

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
    public List<TaskResponseDto> findTasks(MultiValueMap<String, String> conditions) {
        PageRequest pageRequest = null;
        if (conditions.containsKey("page") && conditions.containsKey("size")) {
            pageRequest = PageRequest.of(Integer.parseInt(conditions.get("page").get(0))-1, Integer.parseInt(conditions.get("size").get(0)));
        }
        return taskRepository.findTasks(pageRequest, conditions).getContent();
    }

    @Transactional
    @Override
    public TaskResponseDto updateTask(Long taskId, TaskRequestDto dto) {
        Task task = taskRepository.findTaskById(taskId).orElseThrow(() -> new ResourceNotFoundException(taskId));
        if (!dto.getPassword().equals(task.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your password is wrong.");
        }

        // 덮어씌울 Task 객체 생성, 값 할당
        Task newTask = new Task(
                taskId,
                task.getUserId(),
                // 외래키가 0인 경우 실제 DB와 값이 달라지는 경우 발생
                (task.getUserId() == 0) ? null : dto.getUserName(),
                task.getEmail(),
                task.getPassword(),
                dto.getContent(),
                task.getCreatedDateTime(),
                LocalDateTime.now()
        );
        // tasks 테이블 변경
        taskRepository.updateTask(taskId, newTask);
        // users.user_name 칼럼 변경
        taskRepository.updateUser(task.getUserId(), dto.getUserName());

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
