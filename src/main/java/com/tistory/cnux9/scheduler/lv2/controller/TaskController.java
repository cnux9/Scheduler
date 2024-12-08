package com.tistory.cnux9.scheduler.lv2.controller;

import com.tistory.cnux9.scheduler.lv2.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv2.dto.TaskResponseDto;
import com.tistory.cnux9.scheduler.lv2.service.TaskService;
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
        System.out.println(dto.getName());
        return new ResponseEntity<>(taskService.saveTask(dto), HttpStatus.CREATED);
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> findTaskById(@PathVariable Long id) {
        return new ResponseEntity<>(taskService.findTaskById(id), HttpStatus.OK);
    }

    // 다건 조회
    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> findFilteredTasks(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "date", required = false) LocalDate date) {
        List<TaskResponseDto> taskList;
        if (name == null) {
            if (date == null) {
                taskList = taskService.findAllTasks();
            } else {
                taskList = taskService.findTasksByDate(date);
            }
        } else {
            if (date == null) {
                taskList = taskService.findTasksByName(name);
            } else {
                taskList = taskService.findTasksByNameAndDate(name, date);
            }
        }
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    // 단건 전체 수정
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequestDto dto
    ) {
        return new ResponseEntity<>(taskService.updateTask(id, dto), HttpStatus.OK);
    }

    // 원래 DELETE 메소드 요청에는 바디가 없지만 인증/인가를 아직 못배워서 비밀번호 바디에 전달
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> updateTask(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody
            ) {
        String password = requestBody.get("password");
        taskService.deleteTask(id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
