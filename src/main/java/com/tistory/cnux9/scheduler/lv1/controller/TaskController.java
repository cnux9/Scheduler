package com.tistory.cnux9.scheduler.lv1.controller;

import com.tistory.cnux9.scheduler.lv1.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv1.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv1.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> findTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.findTaskById(id), HttpStatus.OK);
    }

    // 다건 조회d
    @GetMapping
    public void findAllTask() {
        return;
    }
}
