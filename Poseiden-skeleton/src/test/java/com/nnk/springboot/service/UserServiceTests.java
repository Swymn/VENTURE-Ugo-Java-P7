package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.errors.UnknownUser;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.impl.PasswordEncoderServiceImpl;
import com.nnk.springboot.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class UserServiceTests {

    private UserRepository userRepository;
    private UserService userService;
    private PasswordEncoderService encoderService;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        encoderService = new PasswordEncoderServiceImpl();
        userService = new UserServiceImpl(userRepository, encoderService);
    }

    private User getEncryptedUser(final User user) {
        final var rawPassword = user.getPassword();
        final var encodedPassword = encoderService.encode(rawPassword);

        user.setPassword(encodedPassword);
        return user;
    }

    @Test
    void saveUser_shouldSaveUser_newUser() {
        // GIVEN a user
        final var user = new User();

        // WHEN we save the user
        userService.saveUser(user);

        // THEN the user is saved
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void saveUser_shouldEncryptPassword_newUser() {
        // GIVEN a user
        final var user = new User();
        user.setPassword("password");

        // WHEN we save the user
        Mockito.when(userRepository.save(user)).thenReturn(getEncryptedUser(user));
        final var createdUser = userService.saveUser(user);

        // THEN the password is encrypted
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Assertions.assertNotEquals("password", createdUser.getPassword());
    }

    @Test
    void findAllUsers_shouldReturnAllUsers() {
        // GIVEN a user service
        // WHEN we find all users
        userService.findAllUsers();

        // THEN all users are returned
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findUserById_shouldReturnUser_existingUser() {
        // GIVEN a user id
        final var userId = 1;

        // WHEN we find the user by id
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        final var user = userService.findUserById(userId);

        // THEN the user is returned
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    void findUserById_shouldReturnEmpty_existingUser() {
        // GIVEN a user id
        final var userId = 1;

        // WHEN we find the user by id
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        final var user = userService.findUserById(userId);

        // THEN the user is returned
        Mockito.verify(userRepository, Mockito.times(1)).findById(userId);
        Assertions.assertTrue(user.isEmpty());
    }

    @Test
    void updateUser_shouldUpdateUser_existingUser() {
        // GIVEN a user
        final var user = new User();

        // WHEN we update the user
        Mockito.when(userRepository.existsById(user.getId())).thenReturn(true);
        userService.updateUser(user);

        // THEN the user is updated
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void updateUser_shouldEncodeThePassword_existingUser() {
        // GIVEN a user
        final var user = new User();
        user.setPassword("password");

        // WHEN we update the user
        Mockito.when(userRepository.existsById(user.getId())).thenReturn(true);
        Mockito.when(userRepository.save(user)).thenReturn(getEncryptedUser(user));
        final var updatedUser = userService.updateUser(user);

        // THEN the user is updated
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        // AND the password should be encrypted
        Assertions.assertNotEquals("password", updatedUser.getPassword());
    }

    @Test
    void updateUser_shouldThrowUnknownUser_unknownUser() {
        // GIVEN a user
        final var user = new User();

        // WHEN we update the user
        Mockito.when(userRepository.existsById(user.getId())).thenReturn(false);

        // THEN an exception is thrown
        Assertions.assertThrows(UnknownUser.class, () -> userService.updateUser(user));
    }

    @Test
    void deleteUserById_shouldDeleteUser_existingUser() {
        // GIVEN a user id
        final var userId = 1;

        // WHEN we delete the user by id
        Mockito.when(userRepository.existsById(userId)).thenReturn(true);
        userService.deleteUserById(userId);

        // THEN the user is deleted
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

    @Test
    void deleteUserById_shouldThrowUnknownUser_unknownUser() {
        // GIVEN a user id
        final var userId = 1;

        // WHEN we delete the user by id
        Mockito.when(userRepository.existsById(userId)).thenReturn(false);

        // THEN an exception is thrown
        Assertions.assertThrows(UnknownUser.class, () -> userService.deleteUserById(userId));
    }
}
