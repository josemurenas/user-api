package com.exercise.userapi.controller;

import com.exercise.userapi.api.UserApi;
import com.exercise.userapi.model.UserDto;
import com.exercise.userapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public UserDto createUser(UserDto userDto) {
        return userService.createUser(userDto);
    }

    @Override
    public List<UserDto> getUsers() {
       return userService.getUsers();
    }
}
