package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BidListController.class)
class BidListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BidListService bidListService;

    @BeforeEach
    void setUp() {
        bidListService = Mockito.mock(BidListService.class);
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void home_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/list"))
                .andExpect(model().attributeExists("bidLists"));

    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void addBidForm_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void validate_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller and a valid bid
        final var bid = new BidList();
        mockMvc.perform(post("/bidList/validate", bid))
                .andExpect(status().isFound()).andDo(result -> {
                    // THEN the bid should be saved
                    Mockito.verify(bidListService, Mockito.times(1)).saveBidList(bid);
                })
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Disabled
    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void showUpdateForm_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void deleteBid_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/bidList/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void deleteBid_shouldFail_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/bidList/delete/0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/bidList/list"));
    }
}
