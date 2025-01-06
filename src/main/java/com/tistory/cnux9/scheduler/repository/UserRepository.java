package com.tistory.cnux9.scheduler.repository;

import com.tistory.cnux9.scheduler.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
