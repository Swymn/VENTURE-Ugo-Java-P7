package com.nnk.springboot.service.impl;

import com.nnk.springboot.service.PasswordEncoderService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderServiceImpl implements PasswordEncoderService {

    private final PasswordEncoder encoder;

    public PasswordEncoderServiceImpl() {
        this.encoder = new BCryptPasswordEncoder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PasswordEncoder getEncoder() {
        return encoder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String encode(final String password) {
        return encoder.encode(password);
    }
}
