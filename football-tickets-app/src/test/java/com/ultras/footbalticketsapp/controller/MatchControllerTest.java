package com.ultras.footbalticketsapp.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.match.MatchResponse;
import com.ultras.footbalticketsapp.controller.match.NewMatchRequest;
import com.ultras.footbalticketsapp.controller.match.MatchMapper;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

@Disabled
@ContextConfiguration(classes = {MatchController.class})
@ExtendWith(SpringExtension.class)
class MatchControllerTest {
    @Autowired
    private MatchController matchController;

    @MockBean
    private MatchMapper matchMapper;

    @MockBean
    private MatchService matchService;

    /**
     * Method under test: {@link MatchController#saveMatch(NewMatchRequest)}
     */
    @Test
    void testSaveMatch() throws Exception {
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

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);
        when(matchService.saveMatch((Match) any())).thenReturn(match);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");

        FootballTeam footballTeam2 = new FootballTeam();
        footballTeam2.setId(123);
        footballTeam2.setName("Name");
        footballTeam2.setStadium(stadium2);

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");

        FootballTeam footballTeam3 = new FootballTeam();
        footballTeam3.setId(123);
        footballTeam3.setName("Name");
        footballTeam3.setStadium(stadium3);

        Match match1 = new Match();
        match1.setAway_team(footballTeam2);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        match1.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        match1.setHome_team(footballTeam3);
        match1.setId(123);
        match1.setTicket_number(10);
        match1.setTicket_price(10.0d);
        when(matchMapper.matchToMatchResponse((Match) any())).thenReturn(new MatchResponse());
        when(matchMapper.newMatchRequestToMatch((NewMatchRequest) any())).thenReturn(match1);

        Stadium stadium4 = new Stadium();
        stadium4.setCapacity(3);
        stadium4.setId(123);
        stadium4.setName("Name");

        FootballTeam footballTeam4 = new FootballTeam();
        footballTeam4.setId(123);
        footballTeam4.setName("Name");
        footballTeam4.setStadium(stadium4);

        Stadium stadium5 = new Stadium();
        stadium5.setCapacity(3);
        stadium5.setId(123);
        stadium5.setName("Name");

        FootballTeam footballTeam5 = new FootballTeam();
        footballTeam5.setId(123);
        footballTeam5.setName("Name");
        footballTeam5.setStadium(stadium5);

        NewMatchRequest newMatchRequest = new NewMatchRequest();
        newMatchRequest.setAway_team(footballTeam4);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        newMatchRequest.setDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        newMatchRequest.setHome_team(footballTeam5);
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
    void testGetMatchById() throws Exception {
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

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);
        when(matchService.getMatchById(anyInt())).thenReturn(match);
        when(matchMapper.matchToMatchResponse((Match) any())).thenReturn(new MatchResponse());
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
     * Method under test: {@link MatchController#deleteMatch(int)}
     */
    @Test
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
     * Method under test: {@link MatchController#getAllMatches()}
     */
    @Test
    void testGetAllMatches() throws Exception {
        when(matchService.getAllMatches()).thenReturn(new ArrayList<>());
        when(matchMapper.matchesToMatchesResponse((List<Match>) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/matches/");
        MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link MatchController#getNumberOfMatchesByTeam(int)}
     */
    @Test
    void testGetNumberOfMatchesByTeam() throws Exception {
        when(matchService.getNumberOfMatchesByTeam(anyInt())).thenReturn(10);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/matches/team/{teamId}", 123);
        MockMvcBuilders.standaloneSetup(matchController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10"));
    }

    /**
     * Method under test: {@link MatchController#updateMatch(int, MatchResponse)}
     */
    @Test
    void testUpdateMatch() throws Exception {
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

        Match match = new Match();
        match.setAway_team(footballTeam);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        match.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        match.setHome_team(footballTeam1);
        match.setId(123);
        match.setTicket_number(10);
        match.setTicket_price(10.0d);
        when(matchService.updateMatch((Match) any())).thenReturn(match);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");

        FootballTeam footballTeam2 = new FootballTeam();
        footballTeam2.setId(123);
        footballTeam2.setName("Name");
        footballTeam2.setStadium(stadium2);

        Stadium stadium3 = new Stadium();
        stadium3.setCapacity(3);
        stadium3.setId(123);
        stadium3.setName("Name");

        FootballTeam footballTeam3 = new FootballTeam();
        footballTeam3.setId(123);
        footballTeam3.setName("Name");
        footballTeam3.setStadium(stadium3);

        Match match1 = new Match();
        match1.setAway_team(footballTeam2);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        match1.setDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        match1.setHome_team(footballTeam3);
        match1.setId(123);
        match1.setTicket_number(10);
        match1.setTicket_price(10.0d);
        when(matchMapper.matchToMatchResponse((Match) any())).thenReturn(new MatchResponse());
        when(matchMapper.matchResponseToMatch((MatchResponse) any())).thenReturn(match1);

        Stadium stadium4 = new Stadium();
        stadium4.setCapacity(3);
        stadium4.setId(123);
        stadium4.setName("Name");

        FootballTeam footballTeam4 = new FootballTeam();
        footballTeam4.setId(123);
        footballTeam4.setName("Name");
        footballTeam4.setStadium(stadium4);

        Stadium stadium5 = new Stadium();
        stadium5.setCapacity(3);
        stadium5.setId(123);
        stadium5.setName("Name");

        FootballTeam footballTeam5 = new FootballTeam();
        footballTeam5.setId(123);
        footballTeam5.setName("Name");
        footballTeam5.setStadium(stadium5);

        MatchResponse matchResponse = new MatchResponse();
        matchResponse.setAway_team(footballTeam4);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        matchResponse.setDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        matchResponse.setHome_team(footballTeam5);
        matchResponse.setId(123);
        matchResponse.setTicket_number(10);
        matchResponse.setTicket_price(10.0d);
        String content = (new ObjectMapper()).writeValueAsString(matchResponse);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/matches/{matchId}", 123)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
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
     * Method under test: {@link MatchController#deleteMatch(int)}
     */
    @Test
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
}

