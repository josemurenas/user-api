package com.exercise.userapi.service;

import com.exercise.userapi.entity.User;
import com.exercise.userapi.exception.UserNotFoundException;
import com.exercise.userapi.mapper.PhoneMapper;
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
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Spy
    private PhoneMapper phoneMapper = Mappers.getMapper(PhoneMapper.class);

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

    @Test
    void givenExistingId_whenDeleteUSer_thenFinish() {
        //Given
        when(userRepository.existsById(any(UUID.class))).thenReturn(true);

        //When
        userService.deleteUser(UUID.randomUUID());

       verify(userRepository).existsById(any(UUID.class));
       verify(userRepository).deleteById(any(UUID.class));
    }

    @Test
    void givenNonExistentId_whenDeleteUser_thenThrowException() {
        //Given
        when(userRepository.existsById(any(UUID.class))).thenReturn(false);

        //When //Then
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(UUID.randomUUID()));
    }

    @Test
    void givenExistingId_whenDeleteUser_thenFinish() {
        //Given
        when(userRepository.existsById(any(UUID.class))).thenReturn(true);

        //When
        userService.deleteUser(UUID.randomUUID());

        verify(userRepository).existsById(any(UUID.class));
        verify(userRepository).deleteById(any(UUID.class));
    }

    @Test
    void givenExistingId_whenGetUser_thenReturnUser() {
        //Given
        final var id = UUID.randomUUID();
        final var user = getUser(id);
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));

        //When
        final var userDto = userService.getUser(id);

        assertThat(userDto)
                .isNotNull()
                .extracting(UserDto::id, UserDto::name)
                .containsExactly(user.getId(), user.getName());
    }


    @Test
    void givenNonExistentId_whenGetUser_thenThrowException() {
        //Given
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        //When //Then
        assertThrows(UserNotFoundException.class, () -> userService.getUser(UUID.randomUUID()));
    }

    @Test
    void givenExistingId_whenUpdateUser_thenReturnUser() {
        //Given
        final var id = UUID.randomUUID();
        final var user = getUser(id);
        var userDto = userMapper.toDto(user);
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));

        //When
        userDto = userService.updateUser(id, userDto);

        //Then
        assertThat(userDto)
                .isNotNull()
                .extracting(UserDto::id, UserDto::name)
                .containsExactly(user.getId(), user.getName());
    }

    @Test
    void givenNonExistentIdt_whenUpdateUser_thenThrowException() {
        //Given
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        final var id = UUID.randomUUID();
        final var userDto = UserDto.builder().build();

        //When //Then
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(id, userDto));
    }


    private static User getUser(){
      return getUser(UUID.randomUUID());
    }
    private static User getUser(UUID id){
        final var currentDateTime = LocalDateTime.now();
        return User.builder()
                .id(id)
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
