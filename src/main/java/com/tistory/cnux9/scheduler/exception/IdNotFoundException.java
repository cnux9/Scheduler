package com.tistory.cnux9.scheduler.exception;

public class IdNotFoundException extends ResourceNotFoundException {
    public IdNotFoundException(Long id) {
        super("Id does not exist : " + id);
    }
}