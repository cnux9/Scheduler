package com.tistory.cnux9.scheduler.lv6.service;

import com.tistory.cnux9.scheduler.lv6.config.PasswordEncoder;
import com.tistory.cnux9.scheduler.lv6.dto.login.LoginResponseDto;
import com.tistory.cnux9.scheduler.lv6.dto.user.UserRequestDto;
import com.tistory.cnux9.scheduler.lv6.dto.user.UserResponseDto;
import com.tistory.cnux9.scheduler.lv6.entity.User;
import com.tistory.cnux9.scheduler.lv6.repository.UserRepository;
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

        if (!PasswordEncoder.matches(password, foundUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password is wrong : " + password);
        }

        return new LoginResponseDto(foundUser);
    }
}
