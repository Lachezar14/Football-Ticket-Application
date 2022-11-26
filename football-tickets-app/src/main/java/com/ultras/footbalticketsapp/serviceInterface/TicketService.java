package com.ultras.footbalticketsapp.serviceInterface;

import com.ultras.footbalticketsapp.controller.ticket.BuyTicketRequest;
import com.ultras.footbalticketsapp.controller.ticket.TicketResponse;

import java.util.List;

public interface TicketService {
    TicketResponse buyTicket(BuyTicketRequest ticket);
    TicketResponse getTicketById(int id);
    //List<Ticket> getTicketsByMatchId(int matchId);
    List<TicketResponse> getTicketsByUserId(int userId);
    int countAllByTeamId(int teamId);
    double getAVGSaleOfTicketsPerTeam(int teamId);
    //TicketResponse updateTicket(TicketResponse ticket);
    //void deleteTicket(int id);
}
