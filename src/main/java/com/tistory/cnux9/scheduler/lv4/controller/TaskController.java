package com.tistory.cnux9.scheduler.lv4.controller;

import com.tistory.cnux9.scheduler.lv4.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv4.dto.TaskSearchDto;
import com.tistory.cnux9.scheduler.lv4.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(taskService.findTaskById(taskId));
    }

    // 다건 조회
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findFilteredTasks(@ModelAttribute TaskSearchDto dto) {
        return ResponseEntity.ok(taskService.findTasks(dto));
    }

    // 단건 전체 수정
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long taskId,
            @RequestBody TaskRequestDto dto
    ) {
        return ResponseEntity.ok(taskService.updateTask(taskId, dto));
    }

    // 원래 DELETE 메소드 요청에는 바디가 없지만 인증/인가를 아직 못배워서 비밀번호 바디에 전달
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId,
            @RequestBody TaskRequestDto dto
    ) {
        taskService.deleteTask(taskId, dto);
        return ResponseEntity.ok().build();
    }
}
