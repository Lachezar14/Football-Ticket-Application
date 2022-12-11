package com.ultras.footbalticketsapp.tickets;

import com.ultras.footbalticketsapp.controller.ticket.TicketMapper;
import com.ultras.footbalticketsapp.model.*;
import com.ultras.footbalticketsapp.repository.TicketRepository;
import com.ultras.footbalticketsapp.service.TicketServiceImpl;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;
import com.ultras.footbalticketsapp.serviceInterface.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class  TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private TicketMapper ticketMapper;
    @Mock
    private MatchService matchService;
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        ticketService = new TicketServiceImpl(ticketRepository, matchService, ticketMapper);
    }

    @Test
    void testGetTicketById() {
        //given
        User user = new User(1, "bobby", "smurda", "1234567899", "bobby@gmial.com", "12345", AccountType.USER);
        Stadium homeStadium = new Stadium(1, "Stadium", 10000);
        Stadium awayStadium = new Stadium(2, "Stadium2", 10000);
        FootballTeam homeTeam = new FootballTeam(1,"Team1",homeStadium);
        FootballTeam awayTeam = new FootballTeam(2,"Team2",awayStadium);
        Date now = new Date();
        Match match = new Match(1, homeTeam,awayTeam,now,10000,120.0);
        Ticket ticket = new Ticket(1,match,user,120.0);

        //when
        when(ticketRepository.findById((Integer) any())).thenReturn(Optional.of(ticket));
        //when(ticketMapper.ticketToTicketResponse((Ticket) any())).thenReturn(ticketResponse);

        //then
        assertSame(ticket, ticketService.getTicketById(1));
        verify(ticketRepository).findById((Integer) any());
        //verify(ticketMapper).ticketToTicketResponse((Ticket) any());
    }

    @Test
    void testGetTicketsByUserId() {
        //given
        List<Ticket> ticketList = new ArrayList<>();

        //when
        when(ticketRepository.findAllByUserId(anyInt())).thenReturn(new ArrayList<>());
        //when(ticketMapper.ticketsToTicketsResponse((List<Ticket>) any())).thenReturn(ticketResponseList);

        List<Ticket> actualTicketsByUserId = ticketService.getTicketsByUserId(1);

        //then
        assertEquals(ticketList , actualTicketsByUserId);
        verify(ticketRepository).findAllByUserId(anyInt());
        //verify(ticketMapper).ticketsToTicketsResponse((List<Ticket>) any());
    }

}
