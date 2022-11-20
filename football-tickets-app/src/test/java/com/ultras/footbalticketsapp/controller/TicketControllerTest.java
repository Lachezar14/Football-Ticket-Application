package com.ultras.footbalticketsapp.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.ticket.BuyTicketRequest;
import com.ultras.footbalticketsapp.controller.ticket.TicketResponse;
import com.ultras.footbalticketsapp.model.AccountType;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.model.User;
import com.ultras.footbalticketsapp.serviceInterface.TicketService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

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

@ContextConfiguration(classes = {TicketController.class})
@ExtendWith(SpringExtension.class)
class TicketControllerTest {
    @Autowired
    private TicketController ticketController;

    @MockBean
    private TicketService ticketService;

    /**
     * Method under test: {@link TicketController#buyTicket(BuyTicketRequest)}
     */
    @Test
    void testBuyTicket() throws Exception {
        when(ticketService.buyTicket((BuyTicketRequest) any())).thenReturn(new TicketResponse());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);

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

        BuyTicketRequest buyTicketRequest = new BuyTicketRequest();
        buyTicketRequest.setBuyer(user);
        buyTicketRequest.setMatch(match);
        buyTicketRequest.setPrice(10.0d);
        buyTicketRequest.setTicketAmount(1);
        String content = (new ObjectMapper()).writeValueAsString(buyTicketRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/tickets/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"match\":null,\"buyer\":null,\"price\":0.0,\"id\":0}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/tickets/0"));
    }

    /**
     * Method under test: {@link TicketController#getTicketById(int)}
     */
    @Test
    void testGetTicketById() throws Exception {
        when(ticketService.getTicketById(anyInt())).thenReturn(new TicketResponse());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/{ticketId}", 123);
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"match\":null,\"buyer\":null,\"price\":0.0,\"id\":0}"));
    }

    /**
     * Method under test: {@link TicketController#getTicketsByUserId(int)}
     */
    @Test
    void testGetTicketsByUserId() throws Exception {
        when(ticketService.getTicketsByUserId(anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/user/{userId}", 123);
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link TicketController#getTicketsByUserId(int)}
     */
    @Test
    void testGetTicketsByUserId2() throws Exception {
        when(ticketService.getTicketsByUserId(anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/tickets/user/{userId}", 123);
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

