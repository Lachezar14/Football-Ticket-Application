package com.ultras.footbalticketsapp.service;

import com.ultras.footbalticketsapp.controller.ticket.BuyTicketRequest;
import com.ultras.footbalticketsapp.controller.ticket.TicketResponse;
import com.ultras.footbalticketsapp.mapper.TicketMapper;
import com.ultras.footbalticketsapp.model.Ticket;
import com.ultras.footbalticketsapp.repository.TicketRepository;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;
import com.ultras.footbalticketsapp.serviceInterface.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private MatchService matchService;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, MatchService matchService, TicketMapper ticketMapper) {
        this.ticketRepository = ticketRepository;
        this.matchService = matchService;
        this.ticketMapper = ticketMapper;
    }

    @Override
    public TicketResponse buyTicket(BuyTicketRequest buyTicketRequest) {
        Ticket ticket = ticketMapper.buyTicketRequestToTicket(buyTicketRequest);
        if(matchService.TicketBought(ticket.getMatch().getId())){
            ticketRepository.save(ticket);
            return ticketMapper.ticketToTicketResponse(ticket);
        }
        return null;
    }

    @Override
    public TicketResponse getTicketById(int id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        return ticketMapper.ticketToTicketResponse(ticket);
    }

    /*@Override
    public List<Ticket> getTicketsByMatchId(int matchId) {
        return ticketRepository.findAllByMatchId(matchId);
    }*/

    @Override
    public List<TicketResponse> getTicketsByUserId(int userId) {
        return ticketMapper.ticketsToTicketsResponse(ticketRepository.findAllByUserId(userId));
    }

    //TODO remove because ticket data should not be changed
    @Override
    public TicketResponse updateTicket(TicketResponse ticket) {
        Ticket updatedTicket = ticketMapper.ticketResponseToTicket(ticket);
        Ticket ticketToUpdate = ticketRepository.findById(ticket.getId()).orElse(null);
        if(ticketToUpdate == null){
            throw new IllegalStateException("Ticket not found");
        }
        ticketToUpdate.setMatch(updatedTicket.getMatch());
        ticketToUpdate.setBuyer(updatedTicket.getBuyer());
        ticketToUpdate.setPrice(updatedTicket.getPrice());
        ticketRepository.save(ticketToUpdate);
        return ticketMapper.ticketToTicketResponse(ticketToUpdate);
    }

    //TODO should be removed because data of every bought ticket should be kept
    @Override
    public void deleteTicket(int id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        if(ticket == null){
            throw new IllegalStateException("Ticket not found");
        }
        ticketRepository.delete(ticket);
    }
}
