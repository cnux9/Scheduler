package com.tistory.cnux9.scheduler.lv4.controller;

import com.tistory.cnux9.scheduler.lv4.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv4.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv4.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // 단건 생성
    @PostMapping
    public ResponseEntity<TaskResponseDto> save(@Validated @RequestBody TaskRequestDto dto){
        return new ResponseEntity<>(taskService.save(dto), HttpStatus.CREATED);
    }

    // 단건 조회
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> find(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.find(taskId));
    }

    // 다건 조회
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    // 단건 전체 수정
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDto> update(
            @PathVariable Long taskId,
            @Validated @RequestBody TaskRequestDto dto
    ) {
        return ResponseEntity.ok(taskService.update(taskId, dto));
    }

    // 단건 삭제
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long taskId,
            @RequestParam String password
    ) {
        taskService.delete(taskId, password);
        return ResponseEntity.ok().build();
    }
}
