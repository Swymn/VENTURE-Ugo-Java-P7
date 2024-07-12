package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.errors.UnknownUser;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.PasswordEncoderService;
import com.nnk.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderService encoderService;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository, final PasswordEncoderService encoderService) {
        this.userRepository = userRepository;
        this.encoderService = encoderService;
    }

    /**
     * Encodes the password of the given user.
     * @param user The user to encode the password for.
     * @return The user with the encoded password.
     */
    private User encodePassword(final User user) {
        final var rawPassword = user.getPassword();
        if (rawPassword != null) {
            final var encodedPassword = encoderService.encode(rawPassword);
            user.setPassword(encodedPassword);
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User saveUser(final User user) {
        final var encodedUser = encodePassword(user);
        return userRepository.save(encodedUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findUserById(final Integer id) {
        return userRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateUser(final User user) throws UnknownUser {
        if (userRepository.existsById(user.getId())) {
            final var encodedUser = encodePassword(user);
            return userRepository.save(encodedUser);
        } else {
            throw new UnknownUser(user.getId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteUserById(final Integer id) throws UnknownUser {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UnknownUser(id);
        }
    }
}
