package com.tistory.cnux9.scheduler.lv4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tistory.cnux9.scheduler.lv4.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv4.service.TaskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService, ObjectMapper jacksonObjectMapper) {
        this.taskService = taskService;
    }

    // 단건 생성
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequestDto dto){
        return new ResponseEntity<>(taskService.saveTask(dto), HttpStatus.CREATED);
    }

    // 단건 조회
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> findTaskByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.findTaskById(taskId));
    }

    // 다건 조회
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findTasks(@RequestParam MultiValueMap<String, String> conditions) {
        return ResponseEntity.ok(taskService.findTasks(conditions));
    }

    // 단건 전체 수정
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskRequestDto dto
    ) {
        return ResponseEntity.ok(taskService.updateTask(taskId, dto));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId,
            @RequestParam String password
    ) {
        taskService.deleteTask(taskId, password);
        return ResponseEntity.ok().build();
    }
}
