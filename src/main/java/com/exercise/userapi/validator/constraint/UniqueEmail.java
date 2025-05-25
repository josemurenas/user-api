package com.exercise.userapi.validator.constraint;

import com.exercise.userapi.validator.constraintvalidation.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

    String message() default "is already registered";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
