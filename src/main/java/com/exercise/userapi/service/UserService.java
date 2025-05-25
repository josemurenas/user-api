package com.exercise.userapi.service;

import com.exercise.userapi.mapper.UserMapper;
import com.exercise.userapi.model.UserDto;
import com.exercise.userapi.repository.UserRepository;
import com.exercise.userapi.util.JWTGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JWTGenerator jwtGenerator;

    public UserDto createUser(UserDto userDto) {
        var user = userMapper.toEntity(userDto);
        final var token = jwtGenerator.generateToken(user.getName());
        user.setToken(token);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

}
