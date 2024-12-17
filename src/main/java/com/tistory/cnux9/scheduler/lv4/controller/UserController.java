package com.tistory.cnux9.scheduler.lv4.controller;

import com.tistory.cnux9.scheduler.lv4.dto.LoginRequestDto;
import com.tistory.cnux9.scheduler.lv4.dto.LoginResponseDto;
import com.tistory.cnux9.scheduler.lv4.dto.UserRequestDto;
import com.tistory.cnux9.scheduler.lv4.dto.UserResponseDto;
import com.tistory.cnux9.scheduler.lv4.service.UserService;
import com.tistory.cnux9.scheduler.lv4.util.Const;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            HttpServletRequest request,
            // 쿠기 저장을 위해 필요함?
            HttpServletResponse response,
            @Validated @ModelAttribute LoginRequestDto requestDto
    ) {
        HttpSession session = request.getSession();

        // 이미 로그인 된 사용자의 경우 반환
        if (!session.isNew()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        LoginResponseDto responseDto = userService.login(requestDto.getEmail(), requestDto.getPassword());
        session.setAttribute(Const.LOGIN_USER, responseDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
