package com.tistory.cnux9.scheduler.lv1.controller;

import com.tistory.cnux9.scheduler.lv1.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv1.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // 단건 생성
    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@Validated @RequestBody TaskRequestDto dto){
        return new ResponseEntity<>(taskService.saveTask(dto), HttpStatus.CREATED);
    }

    // 단건 조회
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> findTaskByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.findTaskById(taskId));
    }

    // 다건 조회
//    @GetMapping
//    public ResponseEntity<List<TaskResponseDto>> findTasks(@RequestParam MultiValueMap<String, String> conditions) {
//        return ResponseEntity.ok(taskService.findTasks(conditions));
//    }

    // 단건 전체 수정
//    @PutMapping("/{taskId}")
//    public ResponseEntity<TaskResponseDto> updateTask(
//            @PathVariable Long taskId,
//            @Valid @RequestBody TaskRequestDto dto
//    ) {
//        return ResponseEntity.ok(taskService.updateTask(taskId, dto));
//    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long taskId,
            @RequestParam String password
    ) {
        taskService.deleteTask(taskId, password);
        return ResponseEntity.ok().build();
    }
}
