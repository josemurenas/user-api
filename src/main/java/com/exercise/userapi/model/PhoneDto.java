package com.exercise.userapi.model;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record PhoneDto(

        @NotNull
        @Pattern(regexp = "\\d{7}", message = "must be 7 digits")
        String number,

        @NotNull
        @Pattern(regexp = "\\d{1}", message = "must be 1 digits")
        String cityCode,

        @NotNull
        @Pattern(regexp = "\\d{2}", message = "must be 2 digits")
        String countryCode

) {
}
