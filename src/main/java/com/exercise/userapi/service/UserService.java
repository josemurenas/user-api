package com.exercise.userapi.service;

import com.exercise.userapi.entity.User;
import com.exercise.userapi.exception.UserNotFoundException;
import com.exercise.userapi.mapper.PhoneMapper;
import com.exercise.userapi.mapper.UserMapper;
import com.exercise.userapi.model.UserDto;
import com.exercise.userapi.repository.UserRepository;
import com.exercise.userapi.util.JWTGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PhoneMapper phoneMapper;

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

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    public UserDto getUser(UUID id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserDto updateUser(UUID id, UserDto userDto) {
              final var userFound = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
              final var user = getUserUpdated(userFound, userDto);
              userRepository.save(user);
              return userMapper.toDto(user);
    }

    private User getUserUpdated (User oldUser, UserDto newUser) {
        oldUser.setName(newUser.name());
        oldUser.setPassword(newUser.name());
        oldUser.setEmail(newUser.email());
        final var phones = phoneMapper.toEntityList(newUser.phones());
        oldUser.setPhones(phones);
        return  oldUser;
    }


}
