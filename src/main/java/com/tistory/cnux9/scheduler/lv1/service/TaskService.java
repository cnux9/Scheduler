package com.tistory.cnux9.scheduler.lv1.service;

import com.tistory.cnux9.scheduler.lv1.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv1.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv1.entity.Task;
import com.tistory.cnux9.scheduler.lv1.entity.User;
import com.tistory.cnux9.scheduler.lv1.repository.TaskRepository;
import com.tistory.cnux9.scheduler.lv1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

//    public List<TaskResponseDto> findTasks(MultiValueMap<String, String> conditions) {
//        PageRequest pageRequest = null;
//        if (conditions.containsKey("page") && conditions.containsKey("size")) {
//            pageRequest = PageRequest.of(Integer.parseInt(conditions.get("page").get(0))-1, Integer.parseInt(conditions.get("size").get(0)));
//        }
//        return taskRepository.findAllByUserNameAndCreatedDateTime(pageRequest, conditions).getContent();
//    }
//
//    public TaskResponseDto updateTask(Long taskId, TaskRequestDto dto) {
//        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException(taskId));
//        if (!dto.getPassword().equals(task.getPassword())) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your password is wrong.");
//        }
//
//
//
//        // 덮어씌울 Task 객체 생성, 값 할당
//        Task newTask = new Task(
//                taskId,
//                task.getUserId(),
//                // 외래키가 0인 경우 실제 DB와 값이 달라지는 경우 발생
//                (task.getUserId() == 0) ? null : dto.getUserName(),
//                task.getEmail(),
//                task.getPassword(),
//                dto.getContent(),
//                task.getCreatedDateTime(),
//                LocalDateTime.now()
//        );
//        // tasks 테이블 변경
//        taskRepository.updateTaskById(taskId, newTask);
//        // users.user_name 칼럼 변경
//        userRepository.updateNameById(task.getUserId(), dto.getUserName());
//
//        return new TaskResponseDto(newTask);
//    }

    public void deleteTask(Long taskId, String password) {
        Task task = taskRepository.findByIdOrElseThrow(taskId);
        if (!password.equals(task.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Your password is wrong.");
        }
        taskRepository.delete(task);
    }


}
