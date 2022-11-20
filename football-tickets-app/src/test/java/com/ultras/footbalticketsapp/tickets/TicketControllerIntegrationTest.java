package com.ultras.footbalticketsapp.tickets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.TicketController;
import com.ultras.footbalticketsapp.controller.UserController;
import com.ultras.footbalticketsapp.controller.ticket.BuyTicketRequest;
import com.ultras.footbalticketsapp.controller.ticket.TicketResponse;
import com.ultras.footbalticketsapp.model.*;
import com.ultras.footbalticketsapp.serviceInterface.TicketService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;

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
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    void testBuyTicket() throws Exception {
        when(ticketService.buyTicket((BuyTicketRequest) any())).thenReturn(new TicketResponse());

        User user = new User(1, "bobby", "smurda", "1234567899", "bobby@gmial.com", "12345", AccountType.USER);
        Stadium homeStadium = new Stadium(1, "Stadium", 10000);
        Stadium awayStadium = new Stadium(2, "Stadium2", 10000);
        FootballTeam homeTeam = new FootballTeam(1,"Team1",homeStadium);
        FootballTeam awayTeam = new FootballTeam(2,"Team2",awayStadium);
        Date now = new Date();
        Match match = new Match(1, homeTeam,awayTeam,now,10000,120.0);
        BuyTicketRequest buyTicketRequest = new BuyTicketRequest( 1,match,user,120.0);

        mockMvc.perform(post("/tickets/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buyTicketRequest)))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"match\":null,\"buyer\":null,\"price\":0.0,\"id\":0}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/tickets/0"));
    }

    @Test
    void testGetTicketById() throws Exception {
        when(ticketService.getTicketById(anyInt())).thenReturn(new TicketResponse());
        mockMvc.perform(get("/tickets/{ticketId}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"match\":null,\"buyer\":null,\"price\":0.0,\"id\":0}"));
    }

    @Test
    void testGetTicketsByUserId() throws Exception {
        when(ticketService.getTicketsByUserId(anyInt())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/tickets/user/{userId}",1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
