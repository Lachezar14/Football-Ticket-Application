package com.ultras.footbalticketsapp.serviceInterface;

import com.ultras.footbalticketsapp.model.Ticket;

import java.util.List;

public interface TicketService {
    Ticket buyTicket(Ticket ticket,int ticketAmount);
    Ticket getTicketById(int id);
    List<Ticket> getTicketsByUserId(int userId);
    int countAllByTeamId(int teamId);
    double getAVGSaleOfTicketsPerTeam(int teamId);
}
