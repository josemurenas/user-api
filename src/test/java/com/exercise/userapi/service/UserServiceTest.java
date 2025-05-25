package com.exercise.userapi.service;

import com.exercise.userapi.entity.User;
import com.exercise.userapi.mapper.UserMapper;
import com.exercise.userapi.model.UserDto;
import com.exercise.userapi.repository.UserRepository;
import com.exercise.userapi.util.JWTGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mock
    private JWTGenerator jwtGenerator;

    @InjectMocks
    private UserService userService;

    @Test
    void givenValidUserData_whenCreateUser_thenReturnUserDto() {
        //Given
        final var user = getUser();
        final var userDtoRequest = UserDto.builder().name("dummyName").build();
        when(jwtGenerator.generateToken(anyString())).thenReturn("dummyToken");
        when(userRepository.save(any(User.class))).thenReturn(user);

        //When
        final var userDtoResponse = userService.createUser(userDtoRequest);

        //Then
        assertThat(userDtoResponse)
                .isNotNull()
                .extracting(UserDto::id, UserDto::name, UserDto::email, UserDto::password, UserDto::created)
                .containsExactly(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getCreated());
    }

    private static User getUser(){
        final var currentDateTime = LocalDateTime.now();
        return User.builder()
                .id(UUID.randomUUID())
                .name("dummyName")
                .email("dummy@email.com")
                .password(UUID.randomUUID().toString())
                .phones(Collections.emptySet())
                .created(currentDateTime)
                .lastLogin(currentDateTime)
                .isActive(true)
                .build();
    }


}
