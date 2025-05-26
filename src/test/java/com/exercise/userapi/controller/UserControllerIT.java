package com.exercise.userapi.controller;

import com.exercise.userapi.api.UserApi;
import com.exercise.userapi.exception.UserNotFoundException;
import com.exercise.userapi.model.PhoneDto;
import com.exercise.userapi.model.UserDto;
import com.exercise.userapi.repository.UserRepository;
import com.exercise.userapi.service.UserService;
import com.exercise.userapi.validator.constraintvalidation.UniqueEmailValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UniqueEmailValidator emailValidator;
    
    private static final String MESSAGE_ROOT = "$.message";

    @Test
    @SneakyThrows
    void givenUserValidData_whenCreateUser_thenReturnUserDto() {
        //Given
        final var userDtoInput = getValidUserDtoInput();
        final var userDtoOutput = getValidUserDtoOutput();
        when(userService.createUser(any(UserDto.class))).thenReturn(userDtoOutput);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        final var content = objectMapper.writeValueAsString(userDtoInput);

        //When //Then
        mockMvc.perform(post(UserApi.USER_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.id").value(userDtoOutput.id().toString()),
                        jsonPath("$.name").value(userDtoOutput.name()),
                        jsonPath("$.email").value(userDtoOutput.email()),
                        jsonPath("$.password").value(userDtoOutput.password()),
                        jsonPath("$.token").value(userDtoOutput.token().toString()),
                        jsonPath("$.created").isNotEmpty(),
                        jsonPath("$.modified").isNotEmpty(),
                        jsonPath("$.lastLogin").isNotEmpty()
                );
    }

    @SneakyThrows
    @ParameterizedTest
    @NullAndEmptySource
    void givenNullOrBlankName_whenCreateUser_thenBadRequest(String name) {
        //Given
        final var userDtoInput = getValidUserDtoInputBuilder().name(null).build();
        final var userDtoOutput = getValidUserDtoOutput();
        when(userService.createUser(any(UserDto.class))).thenReturn(userDtoOutput);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        final var content = objectMapper.writeValueAsString(userDtoInput);

        //When //Then
        mockMvc.perform(post(UserApi.USER_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath(MESSAGE_ROOT).value("name must not be blank")
                );
    }

    @Test
    @SneakyThrows
    void givenNotUniqueEmail_whenCreateUser_thenBadRequest() {
        //Given
        final var userDtoInput = getValidUserDtoInput();
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        final var content = objectMapper.writeValueAsString(userDtoInput);

        //When //Then
        mockMvc.perform(post(UserApi.USER_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath(MESSAGE_ROOT).value("email is already registered")
                );
    }

    @Test
    @SneakyThrows
    void givenWrongFormatEmail_whenCreateUser_thenBadRequest() {
        //Given
        final var userDtoInput = getValidUserDtoInputBuilder().email("wrongEmail").build();
        final var userDtoOutput = getValidUserDtoOutput();
        when(userService.createUser(any(UserDto.class))).thenReturn(userDtoOutput);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        final var content = objectMapper.writeValueAsString(userDtoInput);

        //When //Then
        mockMvc.perform(post(UserApi.USER_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath(MESSAGE_ROOT).value("email must be a well-formed email address")
                );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @SneakyThrows
    void givenBlankEmail_whenCreateUser_thenBadRequest(String email) {
        //Given
        final var userDtoInput = getValidUserDtoInputBuilder().email(email).build();
        final var userDtoOutput = getValidUserDtoOutput();
        when(userService.createUser(any(UserDto.class))).thenReturn(userDtoOutput);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        final var content = objectMapper.writeValueAsString(userDtoInput);

        //When //Then
        mockMvc.perform(post(UserApi.USER_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath(MESSAGE_ROOT).value("email must not be blank")
                );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @SneakyThrows
    void givenNullOrBlankPassword_whenCreateUser_thenBadRequest(String password) {
        //Given
        final var userDtoInput = getValidUserDtoInputBuilder().password(password).build();
        final var userDtoOutput = getValidUserDtoOutput();
        when(userService.createUser(any(UserDto.class))).thenReturn(userDtoOutput);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        final var content = objectMapper.writeValueAsString(userDtoInput);

        //When //Then
        mockMvc.perform(post(UserApi.USER_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath(MESSAGE_ROOT).value("password must not be blank")
                );
    }

    @Test
    @SneakyThrows
    void givenInvalidPassword_whenCreateUser_thenBadRequest() {
        //Given
        final var userDtoInput = getValidUserDtoInputBuilder().password("wrongPassword").build();
        final var userDtoOutput = getValidUserDtoOutput();
        when(userService.createUser(any(UserDto.class))).thenReturn(userDtoOutput);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        final var content = objectMapper.writeValueAsString(userDtoInput);

        //When //Then
        mockMvc.perform(post(UserApi.USER_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath(MESSAGE_ROOT).value("password should have at least 8 characters, including letters, numbers and a special character.")
                );
    }

    @Test
    @SneakyThrows
    void givenInvalidPhone_whenCreateUser_thenBadRequest() {
        //Given
        final var phone = PhoneDto.builder().number("1234567").countryCode("12").build();
        final var userDtoInput = getValidUserDtoInputBuilder().phones(List.of(phone)).build();
        final var userDtoOutput = getValidUserDtoOutput();
        when(userService.createUser(any(UserDto.class))).thenReturn(userDtoOutput);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        final var content = objectMapper.writeValueAsString(userDtoInput);

        //When //Then
        mockMvc.perform(post(UserApi.USER_URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(
                        jsonPath(MESSAGE_ROOT).value("phones[0].cityCode must not be null")
                );
    }

    @Test
    @SneakyThrows
    void givenNonExistentId_whenDeleteUser_thenReturnNotContent() {
        //When //Then
        mockMvc.perform(delete(UserApi.USER_URL + "/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void givenNonExistentId_whenDeleteUser_thenReturnNotFound() {
        //Given
        final var id = UUID.randomUUID();
        doThrow(new UserNotFoundException(id))
                .when(userService).deleteUser(any(UUID.class));

        //When //Then
        mockMvc.perform(delete(UserApi.USER_URL + "/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath(MESSAGE_ROOT).value(String.format("User with id: %s  - not found", id))
                );
    }

    @Test
    @SneakyThrows
    void givenExistingId_whenFindUser_thenReturnUser() {
        //Given
        final var id = UUID.randomUUID();
        final var userDtoOutput = getValidUserDtoOutput(id);
        when(userService.getUser(any(UUID.class))).thenReturn(userDtoOutput);

        //When //Then
        mockMvc.perform(get(UserApi.USER_URL + "/{id}", id))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(userDtoOutput.id().toString()),
                        jsonPath("$.name").value(userDtoOutput.name()),
                        jsonPath("$.email").value(userDtoOutput.email()),
                        jsonPath("$.password").value(userDtoOutput.password()),
                        jsonPath("$.token").value(userDtoOutput.token().toString()),
                        jsonPath("$.created").isNotEmpty(),
                        jsonPath("$.modified").isNotEmpty(),
                        jsonPath("$.lastLogin").isNotEmpty()
                );
    }

    @Test
    @SneakyThrows
    void givenNonExistentId_whenFindUser_thenReturnNotFound() {
        //Given
        final var id = UUID.randomUUID();
        doThrow(new UserNotFoundException(id))
                .when(userService).getUser(any(UUID.class));

        //When //Then
        mockMvc.perform(get(UserApi.USER_URL + "/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath(MESSAGE_ROOT).value(String.format("User with id: %s  - not found", id))
                );
    }

    @Test
    @SneakyThrows
    void givenExistingId_whenUpdateUser_thenReturnUser() {
        //Given
        final var id = UUID.randomUUID();
        final var userDtoInput = getValidUserDtoInput();
        final var userDtoOutput = getValidUserDtoOutput(id);
        final var content = objectMapper.writeValueAsString(userDtoInput);
        when(userService.updateUser(any(UUID.class), any(UserDto.class))).thenReturn(userDtoOutput);

        //When //Then
        mockMvc.perform(put(UserApi.USER_URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.id").value(userDtoOutput.id().toString()),
                        jsonPath("$.name").value(userDtoOutput.name()),
                        jsonPath("$.email").value(userDtoOutput.email()),
                        jsonPath("$.password").value(userDtoOutput.password()),
                        jsonPath("$.token").value(userDtoOutput.token().toString()),
                        jsonPath("$.created").isNotEmpty(),
                        jsonPath("$.modified").isNotEmpty(),
                        jsonPath("$.lastLogin").isNotEmpty()
                );
    }

    @Test
    @SneakyThrows
    void givenNonExistentId_whenUpdateUser_thenReturnNotFound() {
        //Given
        final var id = UUID.randomUUID();
        final var userDtoInput = getValidUserDtoInput();
        final var content = objectMapper.writeValueAsString(userDtoInput);
        doThrow(new UserNotFoundException(id))
                .when(userService).updateUser(any(UUID.class), any(UserDto.class));

        //When //Then
        mockMvc.perform(put(UserApi.USER_URL + "/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(status().isNotFound())
                .andExpect(
                        jsonPath(MESSAGE_ROOT).value(String.format("User with id: %s  - not found", id))
                );
    }

    private static UserDto getValidUserDtoOutput() {
        return getValidUserDtoOutput(UUID.randomUUID());
    }

    private static UserDto getValidUserDtoOutput(UUID id) {
        final var currentTime = LocalDateTime.now();
        return getValidUserDtoInputBuilder()
                .id(UUID.randomUUID())
                .token("dummyToken")
                .created(currentTime)
                .modified(currentTime)
                .lastLogin(currentTime)
                .isActive(true).build();
    }

    private static UserDto getValidUserDtoInput() {
        return getValidUserDtoInputBuilder().build();
    }

    private static UserDto.UserDtoBuilder getValidUserDtoInputBuilder() {
        final var phone = PhoneDto.builder()
                .number("1234567")
                .countryCode("12")
                .cityCode("1")
                .build();

        return UserDto.builder()
                .name("dummyName")
                .password("dummy12345%")
                .email("testEmail@test.com")
                .phones(List.of(phone));
    }


}
