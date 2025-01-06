package com.tistory.cnux9.scheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IdNotFoundException extends ResourceNotFoundException {
    public IdNotFoundException(Long id) {
        super("Id does not exist : " + id);
    }
}