package com.ultras.footbalticketsapp.serviceInterface;

import com.ultras.footbalticketsapp.model.Ticket;

import java.util.List;

public interface TicketService {
    Ticket saveTicket(Ticket ticket);
    Ticket getTicketById(int id);
    //List<Ticket> getTicketsByMatchId(int matchId);
    List<Ticket> getTicketsByUserId(int userId);
    Ticket updateTicket(Ticket ticket);
    void deleteTicket(int id);
}
