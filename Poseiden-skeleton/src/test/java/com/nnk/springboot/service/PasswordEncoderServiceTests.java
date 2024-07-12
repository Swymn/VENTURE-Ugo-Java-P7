package com.nnk.springboot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PasswordEncoderServiceTests {

    @Autowired
    private PasswordEncoderService passwordEncoderService;

    @Test
    void encode_shouldEncode_givenPassword() {
        // GIVEN a password
        final var password = "password";

        // WHEN encoding the password
        final var encodedPassword = passwordEncoderService.encode(password);

        // THEN the encoded password is not null
        Assertions.assertNotEquals(password, encodedPassword);
    }
}
