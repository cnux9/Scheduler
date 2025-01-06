package com.tistory.cnux9.scheduler.filter;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotLoggedInException extends ResponseStatusException {
    public NotLoggedInException() {
        super(HttpStatus.UNAUTHORIZED, "로그인 해주세요.");
    }
}