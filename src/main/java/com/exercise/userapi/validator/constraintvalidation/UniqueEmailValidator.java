package com.exercise.userapi.validator.constraintvalidation;

import com.exercise.userapi.repository.UserRepository;
import com.exercise.userapi.validator.constraint.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UniqueEmailValidator  implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(email)) {
            return true;
        }
        return !userRepository.existsByEmail(email);
    }
}
