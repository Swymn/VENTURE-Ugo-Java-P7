package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void home_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"))
                .andExpect(model().attributeExists("trades"));

    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void addTradeForm_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void validate_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller and a valid trade
        final var trade = new Trade();
        Mockito.when(tradeService.saveTrade(trade)).thenReturn(trade);

        // WHEN calling the controller
        mockMvc.perform(post("/trade/validate", trade))
                .andExpect(status().isFound()).andDo(result -> {
                    // THEN the trade should be saved
                    Mockito.when(tradeService.saveTrade(trade)).thenReturn(trade);
                })
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void showUpdateForm_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        final var trade = new Trade();
        Mockito.when(tradeService.findTradeById(1)).thenReturn(Optional.of(trade));

        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void showUpdateForm_shouldSucceed_missingBid() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/list"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void deleteTrade_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/trade/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", authorities= {"USER"})
    void deleteTrade_shouldFail_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/trade/delete/0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/trade/list"));
    }
}
