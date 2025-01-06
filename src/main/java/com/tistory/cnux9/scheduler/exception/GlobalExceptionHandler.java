package com.tistory.cnux9.scheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, String> body = new HashMap<>();
        HttpStatus status = (HttpStatus) ex.getStatusCode();

        body.put("status", Integer.toString(status.value()));
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getReason());
        body.put("timestamp", LocalDateTime.now().toString());

        return new ResponseEntity<>(body, status);
    }
}
