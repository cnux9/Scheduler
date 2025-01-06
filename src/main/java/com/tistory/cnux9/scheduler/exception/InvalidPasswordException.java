package com.tistory.cnux9.scheduler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidPasswordException extends ResponseStatusException {
    public InvalidPasswordException() {
        super(HttpStatus.UNAUTHORIZED, "Password is wrong.");
    }
}
