package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
class CurveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurvePointService curvePointService;

    @BeforeEach
    void setUp() {
        curvePointService = Mockito.mock(CurvePointService.class);
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void home_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/list"))
                .andExpect(model().attributeExists("curvePoints"));

    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void addCurveForm_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void validate_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller and a valid curve point
        final var curve = new CurvePoint();
        Mockito.when(curvePointService.saveCurvePoint(curve)).thenReturn(curve);

        // WHEN calling the controller
        mockMvc.perform(post("/curvePoint/validate", curve))
                .andExpect(status().isFound()).andDo(result -> {
                    // THEN the curve point should be saved
                    Mockito.when(curvePointService.saveCurvePoint(curve)).thenReturn(curve);
                })
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @Disabled
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void showUpdateForm_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        final var curvePoint = new CurvePoint();
        Mockito.when(curvePointService.findCurvePointById(1)).thenReturn(Optional.of(curvePoint));

        // WHEN calling the controller
        mockMvc.perform(get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(model().attributeExists("curvePoint"))
                .andExpect(model().attribute("curvePoint", curvePoint));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void deleteCurve_shouldSucceed_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/curvePoint/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    @WithMockUser(username = "user", password = "password", roles= {"USER"})
    void deleteCurve_shouldFail_existingRoute() throws Exception {
        // GIVEN a controller
        mockMvc.perform(get("/curvePoint/delete/0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }
}
