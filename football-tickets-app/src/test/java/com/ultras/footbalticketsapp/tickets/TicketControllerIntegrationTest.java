package com.ultras.footbalticketsapp.tickets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.TicketController;
import com.ultras.footbalticketsapp.controller.ticket.BuyTicketRequest;
import com.ultras.footbalticketsapp.controller.ticket.TicketResponse;
import com.ultras.footbalticketsapp.controller.ticket.TicketMapper;
import com.ultras.footbalticketsapp.model.*;
import com.ultras.footbalticketsapp.serviceInterface.TicketService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest
@ContextConfiguration(classes = {TicketController.class})
@ExtendWith(SpringExtension.class)
public class TicketControllerIntegrationTest {

    @Autowired
    private TicketController ticketController;

    @MockBean
    private TicketMapper ticketMapper;

    @MockBean
    private TicketService ticketService;

    @Test
    void testBuyTicket() throws Exception {
        //given
        User user = new User(1, "bobby", "smurda", "1234567899", "bobby@gmial.com", "12345", AccountType.USER);
        Stadium homeStadium = new Stadium(1, "Stadium", 10000);
        Stadium awayStadium = new Stadium(2, "Stadium2", 10000);
        FootballTeam homeTeam = new FootballTeam(1,"Team1",homeStadium);
        FootballTeam awayTeam = new FootballTeam(2,"Team2",awayStadium);
        Date now = new Date(10);
        Match match = new Match(1, homeTeam,awayTeam,now,10000,120.0);
        BuyTicketRequest buyTicketRequest = new BuyTicketRequest(5,match,user,120.0);
        Ticket ticket = new Ticket(1,match,user,120.0);
        TicketResponse ticketResponse = new TicketResponse(1,match,user,120.0);

        //when
        when(ticketService.buyTicket((Ticket) any(), anyInt())).thenReturn(ticket);
        when(ticketMapper.ticketToTicketResponse((Ticket) any())).thenReturn(ticketResponse);
        when(ticketMapper.buyTicketRequestToTicket((BuyTicketRequest) any())).thenReturn(ticket);

        //then
        String content = (new ObjectMapper()).writeValueAsString(buyTicketRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/tickets/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"match\":{\"home_team\":{\"name\":\"Team1\",\"stadium\":{\"name\":\"Stadium\",\"capacity\":10000,\"id\":1},\"id\":1},\"away_team\":{\"name\":\"Team2\",\"stadium\":{\"name\":\"Stadium2\",\"capacity\":10000,\"id\":2},\"id\":2},\"date\":10,\"ticket_number\":10000,\"ticket_price\":120.0,\"id\":1},\"buyer\":{\"first_name\":\"bobby\",\"last_name\":\"smurda\",\"phone_number\":\"1234567899\",\"email\":\"bobby@gmial.com\",\"password\":\"12345\",\"role\":\"USER\",\"id\":1},\"price\":120.0,\"id\":1}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/tickets/1"));
    }

    @Test
    void testGetTicketById() throws Exception {
        //given
        User user = new User(1, "bobby", "smurda", "1234567899", "bobby@gmial.com", "12345", AccountType.USER);
        Stadium homeStadium = new Stadium(1, "Stadium", 10000);
        Stadium awayStadium = new Stadium(2, "Stadium2", 10000);
        FootballTeam homeTeam = new FootballTeam(1,"Team1",homeStadium);
        FootballTeam awayTeam = new FootballTeam(2,"Team2",awayStadium);
        Date now = new Date(10);
        Match match = new Match(1, homeTeam,awayTeam,now,10000,120.0);

        Ticket ticket = new Ticket(1,match,user,120.0);
        TicketResponse ticketResponse = new TicketResponse(1,match,user,120.0);

        //when
        when(ticketService.getTicketById(anyInt())).thenReturn(ticket);
        when(ticketMapper.ticketToTicketResponse((Ticket) any())).thenReturn(ticketResponse);

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/{ticketId}", 1);
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"match\":{\"home_team\":{\"name\":\"Team1\",\"stadium\":{\"name\":\"Stadium\",\"capacity\":10000,\"id\":1},\"id\":1},\"away_team\":{\"name\":\"Team2\",\"stadium\":{\"name\":\"Stadium2\",\"capacity\":10000,\"id\":2},\"id\":2},\"date\":10,\"ticket_number\":10000,\"ticket_price\":120.0,\"id\":1},\"buyer\":{\"first_name\":\"bobby\",\"last_name\":\"smurda\",\"phone_number\":\"1234567899\",\"email\":\"bobby@gmial.com\",\"password\":\"12345\",\"role\":\"USER\",\"id\":1},\"price\":120.0,\"id\":1}"));
    }

    @Test
    void testGetTicketsByUserId() throws Exception {
        //when
        when(ticketService.getTicketsByUserId(anyInt())).thenReturn(new ArrayList<>());
        when(ticketMapper.ticketsToTicketsResponse((List<Ticket>) any())).thenReturn(new ArrayList<>());

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/user/{userId}", 1);
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetAVGSaleOfTicketsPerTeam() throws Exception {
        //when
        when(ticketService.getAVGSaleOfTicketsPerTeam(anyInt())).thenReturn(10.0d);

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/team/{teamId}", 1);
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10.0"));
    }
}
