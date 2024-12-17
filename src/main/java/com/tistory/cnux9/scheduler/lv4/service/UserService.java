package com.tistory.cnux9.scheduler.lv4.service;

import com.tistory.cnux9.scheduler.lv4.dto.LoginResponseDto;
import com.tistory.cnux9.scheduler.lv4.dto.UserRequestDto;
import com.tistory.cnux9.scheduler.lv4.dto.UserResponseDto;
import com.tistory.cnux9.scheduler.lv4.entity.User;
import com.tistory.cnux9.scheduler.lv4.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto save(UserRequestDto dto) {
        return new UserResponseDto(userRepository.save(new User(dto)));
    }

    public UserResponseDto find(Long userId) {
        return new UserResponseDto(userRepository.findByIdOrElseThrow(userId));
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(UserResponseDto::new).toList();
    }

    @Transactional
    public UserResponseDto update(Long userId, UserRequestDto dto) {
        User foundUser = userRepository.findByIdOrElseThrow(userId);
        foundUser.setUserName(dto.getUserName());
        foundUser.setEmail(dto.getEmail());

        return new UserResponseDto(foundUser);
    }

    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    public LoginResponseDto login(String email, String password) {
        User foundUser = userRepository.findByEmail(email);

        if (foundUser == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email does not exist : " + email);
        }

        if (!foundUser.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is wrong : " + password);
        }

        return new LoginResponseDto(foundUser);
    }
}
