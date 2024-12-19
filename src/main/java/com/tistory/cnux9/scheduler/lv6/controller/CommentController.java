package com.tistory.cnux9.scheduler.lv6.controller;

import com.tistory.cnux9.scheduler.lv6.dto.comment.CommentRequestDto;
import com.tistory.cnux9.scheduler.lv6.dto.comment.CommentResponseDto;
import com.tistory.cnux9.scheduler.lv6.dto.comment.CommentUpdateRequestDto;
import com.tistory.cnux9.scheduler.lv6.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 단건 생성
    @PostMapping
    public ResponseEntity<CommentResponseDto> save(@Validated @RequestBody CommentRequestDto dto){
        return new ResponseEntity<>(commentService.save(dto), HttpStatus.CREATED);
    }

    // 단건 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> find(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.find(commentId));
    }

    // 다건 조회
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAll() {
        return ResponseEntity.ok(commentService.findAll());
    }

    // 단건 전체 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> update(
            @PathVariable Long commentId,
            @Validated @RequestBody CommentUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(commentService.update(commentId, dto));
    }

    // 단건 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long commentId
    ) {
        commentService.delete(commentId);
        return ResponseEntity.ok().build();
    }
}
