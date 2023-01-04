package com.ultras.footbalticketsapp.footballTeams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.FootballTeamController;
import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamResponse;
import com.ultras.footbalticketsapp.controller.footballTeam.NewFootballTeamRequest;
import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamMapper;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.serviceInterface.FootballTeamService;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest
@ContextConfiguration(classes = {FootballTeamController.class})
@ExtendWith(SpringExtension.class)
public class FootballTeamControllerIntegrationTest {

    @Autowired
    private FootballTeamController footballTeamController;

    @MockBean
    private FootballTeamMapper footballTeamMapper;

    @MockBean
    private FootballTeamService footballTeamService;

    @Test
    void testSaveFootballTeam() throws Exception {
        //given
        Stadium stadium = new Stadium(1, "Stadium",10000);
        FootballTeam footballTeam = new FootballTeam(1, "Team", stadium);
        FootballTeamResponse footballTeamResponse = new FootballTeamResponse(1, "Team", stadium);
        NewFootballTeamRequest newFootballTeamRequest = new NewFootballTeamRequest("Team", stadium);

        //when
        when(footballTeamService.saveFootballTeam((FootballTeam) any())).thenReturn(footballTeam);
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenReturn(footballTeamResponse);
        when(footballTeamMapper.newFootballTeamRequestToFootballTeam((NewFootballTeamRequest) any()))
                .thenReturn(footballTeam);

        //then
        String content = (new ObjectMapper()).writeValueAsString(newFootballTeamRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/teams/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"name\":\"Team\",\"stadium\":{\"name\":\"Stadium\",\"capacity\":10000,\"id\":1},\"id\":1}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/teams/1"));
    }

    @Test
    void testGetFootballTeamById() throws Exception {
        //given
        Stadium stadium = new Stadium(1, "Stadium",10000);
        FootballTeam footballTeam = new FootballTeam(1, "Team", stadium);
        FootballTeamResponse footballTeamResponse = new FootballTeamResponse(1, "Team", stadium);

        //when
        when(footballTeamService.getFootballTeamById(anyInt())).thenReturn(footballTeam);
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenReturn(footballTeamResponse);

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teams/{teamId}", 1);
        MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"name\":\"Team\",\"stadium\":{\"name\":\"Stadium\",\"capacity\":10000,\"id\":1},\"id\":1}"));
    }

    @Test
    void testGetAllFootballTeams() throws Exception {
        //when
        when(footballTeamService.getAllFootballTeams()).thenReturn(new ArrayList<>());
        when(footballTeamMapper.footballTeamsToFootballTeamsResponse((List<FootballTeam>) any()))
                .thenReturn(new ArrayList<>());

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teams/");
        MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testUpdateFootballTeam() throws Exception {
        //given
        Stadium stadium = new Stadium(1, "Stadium",10000);
        FootballTeam footballTeam = new FootballTeam(1, "Team", stadium);
        FootballTeamResponse footballTeamResponse = new FootballTeamResponse(1, "Team", stadium);

        //when
        when(footballTeamService.updateFootballTeam((FootballTeam) any())).thenReturn(footballTeam);
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenReturn(footballTeamResponse);
        when(footballTeamMapper.footballTeamResponseToFootballTeam((FootballTeamResponse) any()))
                .thenReturn(footballTeam);

        //then
        String content = (new ObjectMapper()).writeValueAsString(footballTeamResponse);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/teams/{teamId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"name\":\"Team\",\"stadium\":{\"name\":\"Stadium\",\"capacity\":10000,\"id\":1},\"id\":1}"));
    }

    @Test
    void testDeleteFootballTeam() throws Exception {
        //when
        doNothing().when(footballTeamService).deleteFootballTeam(anyInt());

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teams/{teamId}", 1);
        MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Football team deleted successfully"));
    }

}
