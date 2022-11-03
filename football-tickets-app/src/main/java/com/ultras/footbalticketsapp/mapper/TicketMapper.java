package com.ultras.footbalticketsapp.mapper;

import com.ultras.footbalticketsapp.controller.ticket.BuyTicketRequest;
import com.ultras.footbalticketsapp.controller.ticket.TicketResponse;
import com.ultras.footbalticketsapp.model.Ticket;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    Ticket buyTicketRequestToTicket(BuyTicketRequest ticket);
    Ticket ticketResponseToTicket(TicketResponse ticket);
    TicketResponse ticketToTicketResponse(Ticket ticket);
    List<TicketResponse> ticketsToTicketsResponse(List<Ticket> tickets);
}
