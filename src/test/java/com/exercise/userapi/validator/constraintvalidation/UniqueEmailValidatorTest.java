package com.exercise.userapi.validator.constraintvalidation;

import com.exercise.userapi.repository.UserRepository;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UniqueEmailValidatorTest {

    private static final String DUMMY_EMAIL  = "dummyEmail";

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UniqueEmailValidator uniqueEmailValidator;

    @Test
    void GivenNonExistingEmail__WhenValidate__thenReturnTrue(){
        //Given
        final var context = mock(ConstraintValidatorContext.class);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        //When
        final var isValid = uniqueEmailValidator.isValid(DUMMY_EMAIL, context);

        //Then
        assertTrue(isValid);
    }


    @Test
    void GivenExistingEmail__WhenValidate__thenReturnFalse(){
        //Given
        final var context = mock(ConstraintValidatorContext.class);
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        //When
        final var isValid = uniqueEmailValidator.isValid(DUMMY_EMAIL, context);

        //Then
        assertFalse(isValid);
    }
}
