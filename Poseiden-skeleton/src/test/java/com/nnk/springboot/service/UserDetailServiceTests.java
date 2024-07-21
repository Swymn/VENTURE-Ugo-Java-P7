package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.UserSecurity;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

class UserDetailServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_whenUserExists_returnsUserDetails() {
        // Given
        final var user = new User();
        user.setUsername("existingUser");
        user.setPassword("password");
        Mockito.when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(user));

        // When
        final var userDetails = (UserSecurity) userService.loadUserByUsername("existingUser");

        // Then
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals("existingUser", userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_whenUserDoesNotExist_throwsUsernameNotFoundException() {
        // Given
        Mockito.when(userRepository.findByUsername("nonExistingUser")).thenReturn(Optional.empty());

        // When & Then
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("nonExistingUser"));
    }
}
