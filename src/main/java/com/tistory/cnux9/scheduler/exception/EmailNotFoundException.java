package com.tistory.cnux9.scheduler.exception;

public class EmailNotFoundException extends ResourceNotFoundException {
    public EmailNotFoundException(String email) {
        super("Email does not exist : " + email);
    }
}
