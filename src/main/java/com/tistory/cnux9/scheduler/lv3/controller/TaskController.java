package com.tistory.cnux9.scheduler.lv3.controller;

import com.tistory.cnux9.scheduler.lv3.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv3.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv3.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // 단건 생성
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody TaskRequestDto dto) {
        return new ResponseEntity<>(taskService.saveTask(dto), HttpStatus.CREATED);
    }

    // 단건 조회
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> findTaskByTaskId(@PathVariable Long taskId) {
        return new ResponseEntity<>(taskService.findTaskById(taskId), HttpStatus.OK);
    }

    // 다건 조회
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findFilteredTasks(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "date", required = false) LocalDate date) {
        List<TaskResponseDto> taskList;
        if (email == null) {
            if (date == null) {
                taskList = taskService.findAllTasks();
            } else {
                taskList = taskService.findTasksByDate(date);
            }
        } else {
            if (date == null) {
                taskList = taskService.findTasksByEmail(email);
            } else {
                taskList = taskService.findTasksByEmailAndDate(email, date);
            }
        }
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskRequestDto dto
    ) {
        return new ResponseEntity<>(taskService.updateTask(taskId, dto), HttpStatus.OK);
    }

    // 원래 DELETE 메소드 요청에는 바디가 없지만 비밀번호를 http 메시지 바디로 보내는 특이 케이스
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> updateTask(
            @PathVariable Long taskId,
            @RequestBody Map<String, String> requestBody
            ) {
        String password = requestBody.get("password");
        taskService.deleteTask(taskId, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
