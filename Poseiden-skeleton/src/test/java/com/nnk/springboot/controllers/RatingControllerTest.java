package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.BidListService;
import com.nnk.springboot.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingService ratingService;

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void home_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"))
                .andExpect(model().attributeExists("ratings"));

    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void addRatingForm_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void validate_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller and a valid rating
        final var rating = new Rating();
        Mockito.when(ratingService.saveRating(rating)).thenReturn(rating);

        // WHEN calling the controller
        mockMvc.perform(post("/rating/validate", rating))
                .andExpect(status().isFound()).andDo(result -> {
                    // THEN the rating should be saved
                    Mockito.when(ratingService.saveRating(rating)).thenReturn(rating);
                })
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void showUpdateForm_shouldSucceed_missingBid() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/list"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void showUpdateForm_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        final var rating = new Rating();
        Mockito.when(ratingService.findRatingById(1)).thenReturn(Optional.of(rating));

        mockMvc.perform(get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void deleteRating_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/rating/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void deleteRating_shouldFail_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/rating/delete/0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/rating/list"));
    }
}
