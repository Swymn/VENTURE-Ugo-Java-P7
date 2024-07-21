package com.nnk.springboot.service;

import com.nnk.springboot.service.impl.PasswordEncoderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordEncoderServiceTests {

    private PasswordEncoderService passwordEncoderService;

    @BeforeEach
    void setUp() {
        passwordEncoderService = new PasswordEncoderServiceImpl();
    }

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
