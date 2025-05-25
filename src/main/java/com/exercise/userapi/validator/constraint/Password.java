package com.exercise.userapi.validator.constraint;

import com.exercise.userapi.validator.constraintvalidation.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    String message() default "invalid format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
