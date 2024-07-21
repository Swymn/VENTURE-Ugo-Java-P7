package com.nnk.springboot.controllers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void home_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));

    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void addRatingForm_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Disabled
    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void validate_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(post("/rating/validate"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Disabled
    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void showUpdateForm_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void deleteRating_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void deleteRating_shouldFail_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/rating/delete/0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }
}
