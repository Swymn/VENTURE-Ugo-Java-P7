package com.nnk.springboot.controllers;

import com.nnk.springboot.config.PasswordConfiguration;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordConfiguration passwordConfiguration;

    @BeforeEach
    void setUp() {
        Mockito.when(passwordConfiguration.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void home_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"))
                .andExpect(model().attributeExists("users"));

    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void addUserForm_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void validate_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller and a valid user
        final var user = new User();
        Mockito.when(userService.saveUser(user)).thenReturn(user);

        // WHEN calling the controller
        mockMvc.perform(post("/user/validate", user))
                .andExpect(status().isFound()).andDo(result -> {
                    // THEN the user should be saved
                    Mockito.when(userService.saveUser(user)).thenReturn(user);
                })
                .andExpect(view().name("redirect:/user/list"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void showUpdateForm_shouldSucceed_missingBid() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
    }


    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void showUpdateForm_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        final var user = new User();
        Mockito.when(userService.findUserById(1)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void deleteUser_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/user/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void deleteUser_shouldFail_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/user/delete/0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/list"));
    }
}
