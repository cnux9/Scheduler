package com.tistory.cnux9.scheduler.lv2.controller;

import com.tistory.cnux9.scheduler.lv2.dto.TaskRequestDto;
import com.tistory.cnux9.scheduler.lv2.dto.UserRequestDto;
import com.tistory.cnux9.scheduler.lv2.dto.UserResponseDto;
import com.tistory.cnux9.scheduler.lv2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 단건 생성
    @PostMapping
    public ResponseEntity<UserResponseDto> save(@Validated @RequestBody UserRequestDto dto){
        return new ResponseEntity<>(userService.save(dto), HttpStatus.CREATED);
    }

    // 단건 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> find(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.find(userId));
    }

    // 다건 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // 단건 전체 수정
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto> update(
            @PathVariable Long userId,
            @Validated @RequestBody UserRequestDto dto
    ) {
        return ResponseEntity.ok(userService.update(userId, dto));
    }

    //단건 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long userId
    ) {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }
}
