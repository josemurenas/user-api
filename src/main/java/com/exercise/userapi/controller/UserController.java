package com.exercise.userapi.controller;

import com.exercise.userapi.api.UserApi;
import com.exercise.userapi.model.UserDto;
import com.exercise.userapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public UserDto createUser(UserDto userDto) {
        return userService.createUser(userDto);
    }

    @Override
    public UserDto getUser(UUID id) {
        return userService.getUser(id);
    }

    @Override
    public List<UserDto> getUsers() {
       return userService.getUsers();
    }

    @Override
    public void deleteUser(UUID id) {
        userService.deleteUser(id);
    }

    @Override
    public UserDto updateUser(UUID id, UserDto userDto) {
        return userService.updateUser(id, userDto);
    }


}
