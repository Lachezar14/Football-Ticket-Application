package com.ultras.footbalticketsapp.controller;

import com.ultras.footbalticketsapp.controller.ticket.BuyTicketRequest;
import com.ultras.footbalticketsapp.controller.ticket.TicketResponse;
import com.ultras.footbalticketsapp.controller.ticket.TicketMapper;
import com.ultras.footbalticketsapp.model.Ticket;
import com.ultras.footbalticketsapp.serviceInterface.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    @Autowired
    public TicketController(TicketService ticketService, TicketMapper ticketMapper) {
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<TicketResponse> buyTicket(@RequestBody BuyTicketRequest buyTicketRequest){
        Ticket ticket = ticketMapper.buyTicketRequestToTicket(buyTicketRequest);
        TicketResponse savedTicket = ticketMapper.ticketToTicketResponse(ticketService.buyTicket(ticket,buyTicketRequest.getTicketAmount()));
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/tickets/" + savedTicket.getId()).toUriString());
        return ResponseEntity.created(uri).body(savedTicket);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable("ticketId") int ticketId){
        return ResponseEntity.ok().body(ticketMapper.ticketToTicketResponse(ticketService.getTicketById(ticketId)));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketResponse>> getTicketsByUserId(@PathVariable("userId") int userId){
        return ResponseEntity.ok().body(ticketMapper.ticketsToTicketsResponse(ticketService.getTicketsByUserId(userId)));
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<Double> getAVGSaleOfTicketsPerTeam(@PathVariable("teamId") int teamId){
        return ResponseEntity.ok().body(ticketService.getAVGSaleOfTicketsPerTeam(teamId));
    }
}
