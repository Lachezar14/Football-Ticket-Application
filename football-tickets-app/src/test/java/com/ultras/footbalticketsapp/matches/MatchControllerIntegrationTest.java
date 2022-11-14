package com.ultras.footbalticketsapp.matches;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.MatchController;
import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamResponse;
import com.ultras.footbalticketsapp.controller.match.MatchResponse;
import com.ultras.footbalticketsapp.controller.match.NewMatchRequest;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
@ContextConfiguration(classes = {MatchController.class})
@ExtendWith(SpringExtension.class)
public class MatchControllerIntegrationTest {

    @Autowired
    private MatchController matchController;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MatchService matchService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void testSaveMatch() throws Exception {
        when(matchService.saveMatch((NewMatchRequest) any())).thenReturn(new MatchResponse());

        Stadium stadium = new Stadium(1, "Name", 3);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Stadium stadium1 = new Stadium(2, "OtherName", 10);
        FootballTeam footballTeam1 = new FootballTeam(2, "OtherName", stadium1);
        Date now = new Date(); //return current date and time
        NewMatchRequest matchRequest = new NewMatchRequest(footballTeam, footballTeam1, now, 100,10.0);

        mockMvc.perform(post("/matches/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(matchRequest)))
                        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                        .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"home_team\":null,\"away_team\":null,\"date\":null,\"ticket_number\":0,\"ticket_price\":0.0,\"id\":0}"))
                        .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/matches/"));
    }

    @Test
    void testGetMatchById() throws Exception {
        when(matchService.getMatchById(anyInt())).thenReturn(new MatchResponse());
        mockMvc.perform(get("/matches/{matchId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"home_team\":null,\"away_team\":null,\"date\":null,\"ticket_number\":0,\"ticket_price\":0.0,\"id\":0}"));
    }

    @Test
    void testGetAllMatches() throws Exception {
        when(matchService.getAllMatches()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/matches/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    @Disabled
    //TODO fix when the method in the controller is fixed
    void testUpdateMatch() throws Exception {
        when(matchService.updateMatch((MatchResponse) any())).thenReturn(new MatchResponse());

        Stadium stadium = new Stadium(1, "Stadium",10000);
        FootballTeamResponse footballTeamResponse = new FootballTeamResponse(1, "Name", stadium);

        mockMvc.perform(put("/matches/{matchId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(footballTeamResponse)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"home_team\":null,\"away_team\":null,\"date\":null,\"ticket_number\":0,\"ticket_price\":0.0,\"id\":0}"));
    }

    @Test
    void testDeleteMatch() throws Exception {
        doNothing().when(matchService).deleteMatchById(anyInt());
        mockMvc.perform(delete("/matches/{matchId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("Match deleted successfully"));
    }
}
