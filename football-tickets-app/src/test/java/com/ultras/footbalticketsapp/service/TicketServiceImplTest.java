package com.ultras.footbalticketsapp.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ultras.footbalticketsapp.controller.ticket.TicketResponse;
import com.ultras.footbalticketsapp.mapper.TicketMapper;
import com.ultras.footbalticketsapp.model.AccountType;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.model.Ticket;
import com.ultras.footbalticketsapp.model.User;
import com.ultras.footbalticketsapp.repository.TicketRepository;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TicketServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TicketServiceImplTest {
    @MockBean
    private MatchService matchService;

    @MockBean
    private TicketMapper ticketMapper;

    @MockBean
    private TicketRepository ticketRepository;

    @Autowired
    private TicketServiceImpl ticketServiceImpl;

    /**
     * Method under test: {@link TicketServiceImpl#getTicketById(int)}
     */
    @Test
    void testGetTicketById() {
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
        Optional<Ticket> ofResult = Optional.of(ticket);
        when(ticketRepository.findById((Integer) any())).thenReturn(ofResult);
        TicketResponse ticketResponse = new TicketResponse();
        when(ticketMapper.ticketToTicketResponse((Ticket) any())).thenReturn(ticketResponse);
        assertSame(ticketResponse, ticketServiceImpl.getTicketById(1));
        verify(ticketRepository).findById((Integer) any());
        verify(ticketMapper).ticketToTicketResponse((Ticket) any());
    }

    /**
     * Method under test: {@link TicketServiceImpl#getTicketById(int)}
     */
    @Test
    void testGetTicketById2() {
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
        Optional<Ticket> ofResult = Optional.of(ticket);
        when(ticketRepository.findById((Integer) any())).thenReturn(ofResult);
        when(ticketMapper.ticketToTicketResponse((Ticket) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> ticketServiceImpl.getTicketById(1));
        verify(ticketRepository).findById((Integer) any());
        verify(ticketMapper).ticketToTicketResponse((Ticket) any());
    }

    /**
     * Method under test: {@link TicketServiceImpl#getTicketsByUserId(int)}
     */
    @Test
    void testGetTicketsByUserId() {
        when(ticketRepository.findAllByUserId(anyInt())).thenReturn(new ArrayList<>());
        ArrayList<TicketResponse> ticketResponseList = new ArrayList<>();
        when(ticketMapper.ticketsToTicketsResponse((List<Ticket>) any())).thenReturn(ticketResponseList);
        List<TicketResponse> actualTicketsByUserId = ticketServiceImpl.getTicketsByUserId(123);
        assertSame(ticketResponseList, actualTicketsByUserId);
        assertTrue(actualTicketsByUserId.isEmpty());
        verify(ticketRepository).findAllByUserId(anyInt());
        verify(ticketMapper).ticketsToTicketsResponse((List<Ticket>) any());
    }

    /**
     * Method under test: {@link TicketServiceImpl#getTicketsByUserId(int)}
     */
    @Test
    void testGetTicketsByUserId2() {
        when(ticketRepository.findAllByUserId(anyInt())).thenReturn(new ArrayList<>());
        when(ticketMapper.ticketsToTicketsResponse((List<Ticket>) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> ticketServiceImpl.getTicketsByUserId(123));
        verify(ticketRepository).findAllByUserId(anyInt());
        verify(ticketMapper).ticketsToTicketsResponse((List<Ticket>) any());
    }
}

