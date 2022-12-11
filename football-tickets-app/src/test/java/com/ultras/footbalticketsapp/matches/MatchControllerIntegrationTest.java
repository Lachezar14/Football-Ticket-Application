package com.ultras.footbalticketsapp.matches;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.MatchController;
import com.ultras.footbalticketsapp.controller.match.MatchResponse;
import com.ultras.footbalticketsapp.controller.match.NewMatchRequest;
import com.ultras.footbalticketsapp.controller.match.MatchMapper;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@WebMvcTest
@ContextConfiguration(classes = {MatchController.class})
@ExtendWith(SpringExtension.class)
public class MatchControllerIntegrationTest {

    @Autowired
    private MatchController matchController;

    @MockBean
    private MatchMapper matchMapper;

    @MockBean
    private MatchService matchService;

    @Test
    void testSaveMatch() throws Exception {
        //given
        Stadium stadium = new Stadium(1, "Name", 3);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Stadium stadium1 = new Stadium(2, "OtherName", 10);
        FootballTeam footballTeam1 = new FootballTeam(2, "OtherName", stadium1);
        Date now = new Date(10); //return current date and time
        Match match = new Match(1, footballTeam, footballTeam1, now, 100, 10.0);
        NewMatchRequest matchRequest = new NewMatchRequest(footballTeam, footballTeam1, now, 100,10.0);
        MatchResponse matchResponse = new MatchResponse(1, footballTeam, footballTeam1, now, 100, 10.0);

        //when
        when(matchService.saveMatch((Match) any())).thenReturn(match);
        when(matchMapper.matchToMatchResponse((Match) any())).thenReturn(matchResponse);
        when(matchMapper.newMatchRequestToMatch((NewMatchRequest) any())).thenReturn(match);

        //then
        String content = (new ObjectMapper()).writeValueAsString(matchRequest);
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
                                "{\"home_team\":{\"name\":\"Name\",\"stadium\":{\"name\":\"Name\",\"capacity\":3,\"id\":1},\"id\":1},\"away_team\":{\"name\":\"OtherName\",\"stadium\":{\"name\":\"OtherName\",\"capacity\":10,\"id\":2},\"id\":2},\"date\":10,\"ticket_number\":100,\"ticket_price\":10.0,\"id\":1}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/matches/"));
    }

    @Test
    void testGetMatchById() throws Exception {
        //given
        Stadium stadium = new Stadium(1, "Name", 3);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Stadium stadium1 = new Stadium(2, "OtherName", 10);
        FootballTeam footballTeam1 = new FootballTeam(2, "OtherName", stadium1);
        Date now = new Date(10); //return current date and time
        Match match = new Match(1, footballTeam, footballTeam1, now, 100, 10.0);
        MatchResponse matchResponse = new MatchResponse(1, footballTeam, footballTeam1, now, 100, 10.0);

        //when
        when(matchService.getMatchById(anyInt())).thenReturn(match);
        when(matchMapper.matchToMatchResponse((Match) any())).thenReturn(matchResponse);

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/matches/{matchId}", 1);
        MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"home_team\":{\"name\":\"Name\",\"stadium\":{\"name\":\"Name\",\"capacity\":3,\"id\":1},\"id\":1},\"away_team\":{\"name\":\"OtherName\",\"stadium\":{\"name\":\"OtherName\",\"capacity\":10,\"id\":2},\"id\":2},\"date\":10,\"ticket_number\":100,\"ticket_price\":10.0,\"id\":1}"));
    }

    @Test
    void testGetAllMatches() throws Exception {
        //when
        when(matchService.getAllMatches()).thenReturn(new ArrayList<>());
        when(matchMapper.matchesToMatchesResponse((List<Match>) any())).thenReturn(new ArrayList<>());

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/matches/");
        MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetNumberOfMatchesByTeam() throws Exception {
        //when
        when(matchService.getNumberOfMatchesByTeam(anyInt())).thenReturn(10);

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/matches/team/{teamId}", 1);
        MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10"));
    }

    @Test
    // TODO fix when the method in the controller is fixed
    void testUpdateMatch() throws Exception {
        //given
        Stadium stadium = new Stadium(1, "Name", 3);
        FootballTeam footballTeam = new FootballTeam(1, "Name", stadium);
        Stadium stadium1 = new Stadium(2, "OtherName", 10);
        FootballTeam footballTeam1 = new FootballTeam(2, "OtherName", stadium1);
        Date now = new Date(10); //return current date and time
        Match match = new Match(1, footballTeam, footballTeam1, now, 100, 10.0);
        MatchResponse matchResponse = new MatchResponse(1, footballTeam, footballTeam1, now, 100, 10.0);

        //when
        when(matchService.updateMatch((Match) any())).thenReturn(match);
        when(matchMapper.matchToMatchResponse((Match) any())).thenReturn(matchResponse);
        when(matchMapper.matchResponseToMatch((MatchResponse) any())).thenReturn(match);

        //then
        String content = (new ObjectMapper()).writeValueAsString(matchResponse);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matches/{matchId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"home_team\":{\"name\":\"Name\",\"stadium\":{\"name\":\"Name\",\"capacity\":3,\"id\":1},\"id\":1},\"away_team\":{\"name\":\"OtherName\",\"stadium\":{\"name\":\"OtherName\",\"capacity\":10,\"id\":2},\"id\":2},\"date\":10,\"ticket_number\":100,\"ticket_price\":10.0,\"id\":1}"));
    }

    @Test
    void testDeleteMatch() throws Exception {
        //when
        doNothing().when(matchService).deleteMatchById(anyInt());

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/matches/{matchId}", 123);
        MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Match deleted successfully"));
    }
}
