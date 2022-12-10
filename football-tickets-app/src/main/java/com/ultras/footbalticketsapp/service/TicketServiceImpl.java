package com.ultras.footbalticketsapp.service;

import com.ultras.footbalticketsapp.mapper.TicketMapper;
import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.Ticket;
import com.ultras.footbalticketsapp.repository.TicketRepository;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;
import com.ultras.footbalticketsapp.serviceInterface.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final MatchService matchService;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, MatchService matchService, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.matchService = matchService;
        this.ticketMapper = ticketMapper;
    }

    //TODO method need to be refactored because now it works but it is ugly
    @Override
    @Transactional
    public Ticket buyTicket(Ticket buyTicket, int ticketAmount) {
        Match match = matchService.getMatchById(buyTicket.getMatch().getId());
        if(match.getTicket_number() < ticketAmount){
            throw new RuntimeException("Not enough tickets available");
        }
        matchService.TicketBought(match.getId(), ticketAmount);
        for (int i = 0; i < ticketAmount; i++) {
            Ticket saveTicket = new Ticket();
            saveTicket.setBuyer(buyTicket.getBuyer());
            saveTicket.setMatch(buyTicket.getMatch());
            saveTicket.setPrice(buyTicket.getPrice());
            ticketRepository.save(saveTicket);
        }
        return buyTicket;
    }

    @Override
    public Ticket getTicketById(int id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        return ticket;
    }

    @Override
    public List<Ticket> getTicketsByUserId(int userId) {
        return ticketRepository.findAllByUserId(userId);
    }

    @Override
    public int countAllByTeamId(int teamId){
        return ticketRepository.countAllByTeamId(teamId);
    }

    @Override
    public double getAVGSaleOfTicketsPerTeam(int teamId){
        return ticketRepository.getAVGSaleOfTicketsPerTeam(teamId);
    }
}
