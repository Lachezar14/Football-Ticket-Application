package com.ultras.footbalticketsapp.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamResponse;
import com.ultras.footbalticketsapp.controller.footballTeam.NewFootballTeamRequest;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.serviceInterface.FootballTeamService;

import java.util.ArrayList;

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

@ContextConfiguration(classes = {FootballTeamController.class})
@ExtendWith(SpringExtension.class)
class FootballTeamControllerTest {
    @Autowired
    private FootballTeamController footballTeamController;

    @MockBean
    private FootballTeamService footballTeamService;

    /**
     * Method under test: {@link FootballTeamController#saveFootballTeam(NewFootballTeamRequest)}
     */

    @Test
    @Disabled
    void testSaveFootballTeam() throws Exception {
        when(footballTeamService.saveFootballTeam((NewFootballTeamRequest) any())).thenReturn(new FootballTeamResponse());

        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        NewFootballTeamRequest newFootballTeamRequest = new NewFootballTeamRequest();
        newFootballTeamRequest.setName("Name");
        newFootballTeamRequest.setStadium(stadium);
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
    @Disabled
    void testGetFootballTeamById() throws Exception {
        when(footballTeamService.getFootballTeamById(anyInt())).thenReturn(new FootballTeamResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teams/{teamId}", 123);
        MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"name\":null,\"stadium\":null,\"id\":0}"));
    }

    /**
     * Method under test: {@link FootballTeamController#getFootballTeamById(int)}
     */
    @Test
    @Disabled
    void testGetFootballTeamById2() throws Exception {
        when(footballTeamService.getAllFootballTeams()).thenReturn(new ArrayList<>());
        when(footballTeamService.getFootballTeamById(anyInt())).thenReturn(new FootballTeamResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teams/{teamId}", "", "Uri Variables");
        MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link FootballTeamController#getAllFootballTeams()}
     */
    @Test
    @Disabled
    void testGetAllFootballTeams() throws Exception {
        when(footballTeamService.getAllFootballTeams()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/teams/");
        MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link FootballTeamController#getAllFootballTeams()}
     */
    @Test
    @Disabled
    void testGetAllFootballTeams2() throws Exception {
        when(footballTeamService.getAllFootballTeams()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/teams/");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(footballTeamController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link FootballTeamController#updateFootballTeam(FootballTeamResponse)}
     */
    @Test
    @Disabled
    void testUpdateFootballTeam() throws Exception {
        when(footballTeamService.updateFootballTeam((FootballTeamResponse) any())).thenReturn(new FootballTeamResponse());

        Stadium stadium = new Stadium();
        stadium.setCapacity(3);
        stadium.setId(123);
        stadium.setName("Name");

        FootballTeamResponse footballTeamResponse = new FootballTeamResponse();
        footballTeamResponse.setId(123);
        footballTeamResponse.setName("Name");
        footballTeamResponse.setStadium(stadium);
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
    @Disabled
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
    @Disabled
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

