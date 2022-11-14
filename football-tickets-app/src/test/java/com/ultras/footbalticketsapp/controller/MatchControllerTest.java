package com.ultras.footbalticketsapp.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.match.MatchResponse;
import com.ultras.footbalticketsapp.controller.match.NewMatchRequest;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {MatchController.class})
@ExtendWith(SpringExtension.class)
class MatchControllerTest {
    @Autowired
    private MatchController matchController;

    @MockBean
    private MatchService matchService;

    /**
     * Method under test: {@link MatchController#deleteMatch(int)}
     */

    @Test
    @Disabled
    void testDeleteMatch() throws Exception {
        doNothing().when(matchService).deleteMatchById(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/matches/{matchId}", 123);
        MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Match deleted successfully"));
    }

    /**
     * Method under test: {@link MatchController#deleteMatch(int)}
     */
    @Test
    @Disabled
    void testDeleteMatch2() throws Exception {
        doNothing().when(matchService).deleteMatchById(anyInt());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/matches/{matchId}", 123);
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Match deleted successfully"));
    }

    /**
     * Method under test: {@link MatchController#saveMatch(NewMatchRequest)}
     */
    @Test
    @Disabled
    void testSaveMatch() throws Exception {
        when(matchService.saveMatch((NewMatchRequest) any())).thenReturn(new MatchResponse());

        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);

        NewMatchRequest newMatchRequest = new NewMatchRequest();
        newMatchRequest.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        newMatchRequest.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        newMatchRequest.setHome_team(footballTeam1);
        newMatchRequest.setTicket_number(10);
        newMatchRequest.setTicket_price(10.0d);
        String content = (new ObjectMapper()).writeValueAsString(newMatchRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/matches/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"home_team\":null,\"away_team\":null,\"date\":null,\"ticket_number\":0,\"ticket_price\":0.0,\"id\":0}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/matches/"));
    }

    /**
     * Method under test: {@link MatchController#getMatchById(int)}
     */
    @Test
    @Disabled
    void testGetMatchById() throws Exception {
        when(matchService.getMatchById(anyInt())).thenReturn(new MatchResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/matches/{matchId}", 123);
        MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"home_team\":null,\"away_team\":null,\"date\":null,\"ticket_number\":0,\"ticket_price\":0.0,\"id\":0}"));
    }

    /**
     * Method under test: {@link MatchController#getMatchById(int)}
     */
    @Test
    @Disabled
    void testGetMatchById2() throws Exception {
        when(matchService.getAllMatches()).thenReturn(new ArrayList<>());
        when(matchService.getMatchById(anyInt())).thenReturn(new MatchResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/matches/{matchId}", "",
                "Uri Variables");
        MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link MatchController#getAllMatches()}
     */
    @Test
    @Disabled
    void testGetAllMatches() throws Exception {
        when(matchService.getAllMatches()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/matches/");
        MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link MatchController#getAllMatches()}
     */
    @Test
    @Disabled
    void testGetAllMatches2() throws Exception {
        when(matchService.getAllMatches()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/matches/");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link MatchController#updateMatch(MatchResponse)}
     */
    @Test
    @Disabled
    void testUpdateMatch() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matches/{matchId}",
                new MatchResponse());
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }
}

