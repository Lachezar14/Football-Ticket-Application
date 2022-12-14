package com.ultras.footbalticketsapp.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.ticket.BuyTicketRequest;
import com.ultras.footbalticketsapp.controller.ticket.TicketResponse;
import com.ultras.footbalticketsapp.controller.ticket.TicketMapper;
import com.ultras.footbalticketsapp.model.AccountType;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.model.Ticket;
import com.ultras.footbalticketsapp.model.User;
import com.ultras.footbalticketsapp.serviceInterface.TicketService;

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
@ContextConfiguration(classes = {TicketController.class})
@ExtendWith(SpringExtension.class)
class TicketControllerTest {
    @Autowired
    private TicketController ticketController;

    @MockBean
    private TicketMapper ticketMapper;

    @MockBean
    private TicketService ticketService;

    /**
     * Method under test: {@link TicketController#buyTicket(BuyTicketRequest)}
     */
    @Test
    void testBuyTicket() throws Exception {
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

        Ticket ticket = new Ticket();
        ticket.setBuyer(user);
        ticket.setId(123);
        ticket.setMatch(match);
        ticket.setPrice(10.0d);
        when(ticketService.buyTicket((Ticket) any(), anyInt())).thenReturn(ticket);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirst_name("Jane");
        user1.setId(123);
        user1.setLast_name("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone_number("4105551212");
        user1.setRole(AccountType.ADMIN);

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

        Ticket ticket1 = new Ticket();
        ticket1.setBuyer(user1);
        ticket1.setId(123);
        ticket1.setMatch(match1);
        ticket1.setPrice(10.0d);
        when(ticketMapper.ticketToTicketResponse((Ticket) any())).thenReturn(new TicketResponse());
        when(ticketMapper.buyTicketRequestToTicket((BuyTicketRequest) any())).thenReturn(ticket1);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirst_name("Jane");
        user2.setId(123);
        user2.setLast_name("Doe");
        user2.setPassword("iloveyou");
        user2.setPhone_number("4105551212");
        user2.setRole(AccountType.ADMIN);

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

        Match match2 = new Match();
        match2.setAway_team(footballTeam4);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        match2.setDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        match2.setHome_team(footballTeam5);
        match2.setId(123);
        match2.setTicket_number(10);
        match2.setTicket_price(10.0d);

        BuyTicketRequest buyTicketRequest = new BuyTicketRequest();
        buyTicketRequest.setBuyer(user2);
        buyTicketRequest.setMatch(match2);
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

        Ticket ticket = new Ticket();
        ticket.setBuyer(user);
        ticket.setId(123);
        ticket.setMatch(match);
        ticket.setPrice(10.0d);
        when(ticketService.getTicketById(anyInt())).thenReturn(ticket);
        when(ticketMapper.ticketToTicketResponse((Ticket) any())).thenReturn(new TicketResponse());
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
        when(ticketMapper.ticketsToTicketsResponse((List<Ticket>) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/user/{userId}", 123);
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link TicketController#getAVGSaleOfTicketsPerTeam(int)}
     */
    @Test
    void testGetAVGSaleOfTicketsPerTeam() throws Exception {
        when(ticketService.getAVGSaleOfTicketsPerTeam(anyInt())).thenReturn(10.0d);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/tickets/team/{teamId}", 123);
        MockMvcBuilders.standaloneSetup(ticketController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("10.0"));
    }
}

