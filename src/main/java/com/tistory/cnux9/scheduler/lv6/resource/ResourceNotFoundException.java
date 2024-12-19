package com.tistory.cnux9.scheduler.lv6.resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResourceNotFoundException extends ResponseStatusException {

    public ResourceNotFoundException(Long taskId) {
        super(HttpStatus.NOT_FOUND, "Does not exist task_id = " + taskId);
    }
}
