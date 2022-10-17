package com.ultras.footbalticketsapp.service;

import com.ultras.footbalticketsapp.model.Ticket;
import com.ultras.footbalticketsapp.repository.TicketRepository;
import com.ultras.footbalticketsapp.serviceInterface.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket getTicketById(int id) {
        return ticketRepository.findById(id).orElse(null);
    }

    /*@Override
    public List<Ticket> getTicketsByMatchId(int matchId) {
        return ticketRepository.findAllByMatchId(matchId);
    }*/

    @Override
    public List<Ticket> getTicketsByUserId(int userId) {
        return ticketRepository.findAllByUserId(userId);
    }

    @Override
    public Ticket updateTicket(Ticket ticket) {
        return null;
    }

    @Override
    public void deleteTicket(int id) {
        Ticket ticket = ticketRepository.findById(id).orElse(null);
        ticketRepository.delete(ticket);
    }
}
