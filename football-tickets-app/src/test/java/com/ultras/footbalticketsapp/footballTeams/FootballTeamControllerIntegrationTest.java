package com.ultras.footbalticketsapp.footballTeams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.FootballTeamController;
import com.ultras.footbalticketsapp.controller.UserController;
import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamResponse;
import com.ultras.footbalticketsapp.controller.footballTeam.NewFootballTeamRequest;
import com.ultras.footbalticketsapp.controller.match.MatchResponse;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.serviceInterface.FootballTeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
@ContextConfiguration(classes = {FootballTeamController.class})
@ExtendWith(SpringExtension.class)
public class FootballTeamControllerIntegrationTest {

    @Autowired
    private FootballTeamController teamController;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FootballTeamService teamService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void testSaveFootballTeam() throws Exception {
        when(teamService.saveFootballTeam((NewFootballTeamRequest) any())).thenReturn(new FootballTeamResponse());

        Stadium stadium = new Stadium(1, "Stadium",10000);
        NewFootballTeamRequest newFootballTeamRequest = new NewFootballTeamRequest("Name", stadium);

        mockMvc.perform(post("/teams/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newFootballTeamRequest)))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"name\":null,\"stadium\":null,\"id\":0}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/teams/0"));
    }

    @Test
    void testGetFootballTeamById() throws Exception {
        when(teamService.getFootballTeamById(anyInt())).thenReturn(new FootballTeamResponse());
        mockMvc.perform(get("/teams/{teamId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"name\":null,\"stadium\":null,\"id\":0}"));
    }

    @Test
    void testGetAllFootballTeams() throws Exception {
        when(teamService.getAllFootballTeams()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/teams/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testUpdateFootballTeam() throws Exception {
        when(teamService.updateFootballTeam((FootballTeamResponse) any())).thenReturn(new FootballTeamResponse());

        Stadium stadium = new Stadium(1, "Stadium",10000);
        FootballTeamResponse footballTeamResponse = new FootballTeamResponse(1, "Name", stadium);

        mockMvc.perform(put("/teams/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(footballTeamResponse)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"name\":null,\"stadium\":null,\"id\":0}"));
    }

    @Test
    void testDeleteFootballTeam() throws Exception {
        doNothing().when(teamService).deleteFootballTeam(anyInt());

        mockMvc.perform(delete("/teams/{teamId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("Football team deleted successfully"));
    }


}
