package com.exercise.userapi.validator.constraintvalidation;

import com.exercise.userapi.validator.constraint.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PasswordValidator implements ConstraintValidator<Password, String>  {

    @Value("${spring.application.password-regex}")
    private final String regex;

    @Value("${spring.application.password-regex-message}")
    private final String message;

    private Pattern pattern;

    @Override
    public void initialize(Password annotation) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(value)) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                 .addConstraintViolation();
        return pattern.matcher(value).matches();
    }
}
