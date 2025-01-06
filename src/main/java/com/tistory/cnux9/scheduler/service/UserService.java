package com.tistory.cnux9.scheduler.service;

import com.tistory.cnux9.scheduler.config.PasswordEncoder;
import com.tistory.cnux9.scheduler.dto.login.LoginResponseDto;
import com.tistory.cnux9.scheduler.dto.user.UserRequestDto;
import com.tistory.cnux9.scheduler.dto.user.UserResponseDto;
import com.tistory.cnux9.scheduler.entity.User;
import com.tistory.cnux9.scheduler.exception.IdNotFoundException;
import com.tistory.cnux9.scheduler.exception.InvalidPasswordException;
import com.tistory.cnux9.scheduler.exception.EmailNotFoundException;
import com.tistory.cnux9.scheduler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto save(UserRequestDto dto) {
        return new UserResponseDto(userRepository.save(new User(dto)));
    }

    @Transactional(readOnly = true)
    public UserResponseDto find(Long userId) {
        return new UserResponseDto(findByIdOrElseThrow(userId));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(UserResponseDto::new).toList();
    }

    public UserResponseDto update(Long userId, UserRequestDto dto) {
        User foundUser = findByIdOrElseThrow(userId);
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
            throw new EmailNotFoundException(email);
        }

        if (!PasswordEncoder.matches(password, foundUser.getPassword())) {
            throw new InvalidPasswordException();
        }

        return new LoginResponseDto(foundUser);
    }

    User findByIdOrElseThrow(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }
}
