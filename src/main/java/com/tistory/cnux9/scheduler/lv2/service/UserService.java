package com.tistory.cnux9.scheduler.lv2.service;

import com.tistory.cnux9.scheduler.lv2.dto.UserRequestDto;
import com.tistory.cnux9.scheduler.lv2.dto.UserResponseDto;
import com.tistory.cnux9.scheduler.lv2.entity.User;
import com.tistory.cnux9.scheduler.lv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
