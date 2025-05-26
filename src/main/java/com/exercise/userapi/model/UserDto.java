package com.exercise.userapi.model;

import com.exercise.userapi.validator.constraint.Password;
import com.exercise.userapi.validator.constraint.UniqueEmail;
import com.exercise.userapi.validator.group.OnCreate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.Default;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
public record UserDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,

        @NotBlank
        String name,

        @Email
        @UniqueEmail(groups = {OnCreate.class})
        @NotBlank
        String email,

        @NotBlank
        @Password
        String password,

        @Valid List<PhoneDto> phones,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        LocalDateTime created,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        LocalDateTime modified,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        LocalDateTime lastLogin,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        boolean isActive,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String token
) {
}
