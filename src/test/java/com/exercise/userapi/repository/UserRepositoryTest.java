package com.exercise.userapi.repository;

import com.exercise.userapi.entity.Phone;
import com.exercise.userapi.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void givenValidUser_whenSaveAndFind_thenUserReturnWithId() {
        //Given
        var user = getUser();
        user = userRepository.save(user);

        //When
        final var userFound = userRepository.findById(user.getId());

        //Then
        assertThat(userFound)
                .isPresent()
                .get()
                .extracting(User::getId, User::getName, u -> u.getPhones().size())
                .containsExactly(user.getId(), user.getName(), 1);

        final var phones = userFound.get().getPhones();
        assertThat(phones)
                .isNotEmpty()
                .first()
                .extracting(Phone::getId)
                .isNotNull();
    }

    private static User getUser() {
        final var phone = Phone.builder().number("1234567")
                .cityCode("1")
                .countryCode("12")
                .build();
        return User.builder()
                .name("dummyName")
                .password("1234#abcd")
                .email("myemail@test.com")
                .phones(Set.of(phone)).build();
    }

}
