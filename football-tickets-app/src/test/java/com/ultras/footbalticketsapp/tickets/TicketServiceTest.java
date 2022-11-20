package com.ultras.footbalticketsapp.tickets;

import com.ultras.footbalticketsapp.controller.ticket.BuyTicketRequest;
import com.ultras.footbalticketsapp.controller.ticket.TicketResponse;
import com.ultras.footbalticketsapp.mapper.TicketMapper;
import com.ultras.footbalticketsapp.mapper.UserMapper;
import com.ultras.footbalticketsapp.model.*;
import com.ultras.footbalticketsapp.repository.TicketRepository;
import com.ultras.footbalticketsapp.repository.UserRepository;
import com.ultras.footbalticketsapp.service.TicketServiceImpl;
import com.ultras.footbalticketsapp.service.UserServiceImpl;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;
import com.ultras.footbalticketsapp.serviceInterface.TicketService;
import com.ultras.footbalticketsapp.serviceInterface.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        TicketResponse ticketResponse = new TicketResponse(1,match,user,120.0);

        //when
        when(ticketRepository.findById((Integer) any())).thenReturn(Optional.of(ticket));
        when(ticketMapper.ticketToTicketResponse((Ticket) any())).thenReturn(ticketResponse);

        //then
        assertSame(ticketResponse, ticketService.getTicketById(1));
        verify(ticketRepository).findById((Integer) any());
        verify(ticketMapper).ticketToTicketResponse((Ticket) any());
    }

    @Test
    void testGetTicketsByUserId() {
        //given
        ArrayList<TicketResponse> ticketResponseList = new ArrayList<>();

        //when
        when(ticketRepository.findAllByUserId(anyInt())).thenReturn(new ArrayList<>());
        when(ticketMapper.ticketsToTicketsResponse((List<Ticket>) any())).thenReturn(ticketResponseList);

        List<TicketResponse> actualTicketsByUserId = ticketService.getTicketsByUserId(1);

        //then
        assertSame(ticketResponseList, actualTicketsByUserId);
        verify(ticketRepository).findAllByUserId(anyInt());
        verify(ticketMapper).ticketsToTicketsResponse((List<Ticket>) any());
    }

}
