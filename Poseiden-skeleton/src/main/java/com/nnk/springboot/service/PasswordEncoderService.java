package com.nnk.springboot.service;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This interface is used to define the password encoder service.
 */
public interface PasswordEncoderService {

    /**
     * This method is used to get the encoder.
     * @return The encoder.
     */
    PasswordEncoder getEncoder();

    /**
     * This method is used to encode a password.
     * @param password The password to encode.
     * @return The encoded password.
     */
    String encode(final String password);
}
