package com.ultras.footbalticketsapp.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamResponse;
import com.ultras.footbalticketsapp.controller.footballTeam.NewFootballTeamRequest;
import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamMapper;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.serviceInterface.FootballTeamService;

import java.util.ArrayList;
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
@ContextConfiguration(classes = {FootballTeamController.class})
@ExtendWith(SpringExtension.class)
class FootballTeamControllerTest {
    @Autowired
    private FootballTeamController footballTeamController;

    @MockBean
    private FootballTeamMapper footballTeamMapper;

    @MockBean
    private FootballTeamService footballTeamService;

    /**
     * Method under test: {@link FootballTeamController#saveFootballTeam(NewFootballTeamRequest)}
     */
    @Test
    void testSaveFootballTeam() throws Exception {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);
        when(footballTeamService.saveFootballTeam((FootballTeam) any())).thenReturn(footballTeam);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenReturn(new FootballTeamResponse());
        when(footballTeamMapper.newFootballTeamRequestToFootballTeam((NewFootballTeamRequest) any()))
                .thenReturn(footballTeam1);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");

        NewFootballTeamRequest newFootballTeamRequest = new NewFootballTeamRequest();
        newFootballTeamRequest.setName("Name");
        newFootballTeamRequest.setStadium(stadium2);
        String content = (new ObjectMapper()).writeValueAsString(newFootballTeamRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/teams/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"name\":null,\"stadium\":null,\"id\":0}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/teams/0"));
    }

    /**
     * Method under test: {@link FootballTeamController#getFootballTeamById(int)}
     */
    @Test
    void testGetFootballTeamById() throws Exception {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);
        when(footballTeamService.getFootballTeamById(anyInt())).thenReturn(footballTeam);
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenReturn(new FootballTeamResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teams/{teamId}", 123);
        MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"name\":null,\"stadium\":null,\"id\":0}"));
    }

    /**
     * Method under test: {@link FootballTeamController#getAllFootballTeams()}
     */
    @Test
    void testGetAllFootballTeams() throws Exception {
        when(footballTeamService.getAllFootballTeams()).thenReturn(new ArrayList<>());
        when(footballTeamMapper.footballTeamsToFootballTeamsResponse((List<FootballTeam>) any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teams/");
        MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link FootballTeamController#updateFootballTeam(FootballTeamResponse)}
     */
    @Test
    void testUpdateFootballTeam() throws Exception {
        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeam footballTeam = new FootballTeam();
        footballTeam.setId(123);
        footballTeam.setName("Name");
        footballTeam.setStadium(stadium);
        when(footballTeamService.updateFootballTeam((FootballTeam) any())).thenReturn(footballTeam);

        Stadium stadium1 = new Stadium();
        stadium1.setCapacity(3);
        stadium1.setId(123);
        stadium1.setName("Name");

        FootballTeam footballTeam1 = new FootballTeam();
        footballTeam1.setId(123);
        footballTeam1.setName("Name");
        footballTeam1.setStadium(stadium1);
        when(footballTeamMapper.footballTeamToFootballTeamResponse((FootballTeam) any()))
                .thenReturn(new FootballTeamResponse());
        when(footballTeamMapper.footballTeamResponseToFootballTeam((FootballTeamResponse) any()))
                .thenReturn(footballTeam1);

        Stadium stadium2 = new Stadium();
        stadium2.setCapacity(3);
        stadium2.setId(123);
        stadium2.setName("Name");

        FootballTeamResponse footballTeamResponse = new FootballTeamResponse();
        footballTeamResponse.setId(123);
        footballTeamResponse.setName("Name");
        footballTeamResponse.setStadium(stadium2);
        String content = (new ObjectMapper()).writeValueAsString(footballTeamResponse);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/teams/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"name\":null,\"stadium\":null,\"id\":0}"));
    }

    /**
     * Method under test: {@link FootballTeamController#deleteFootballTeam(int)}
     */
    @Test
    void testDeleteFootballTeam() throws Exception {
        doNothing().when(footballTeamService).deleteFootballTeam(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/teams/{teamId}", 123);
        MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Football team deleted successfully"));
    }

    /**
     * Method under test: {@link FootballTeamController#deleteFootballTeam(int)}
     */
    @Test
    void testDeleteFootballTeam2() throws Exception {
        doNothing().when(footballTeamService).deleteFootballTeam(anyInt());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/teams/{teamId}", 123);
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Football team deleted successfully"));
    }
}

