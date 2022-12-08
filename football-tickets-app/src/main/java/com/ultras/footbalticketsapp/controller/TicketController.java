package com.ultras.footbalticketsapp.controller;

import com.ultras.footbalticketsapp.controller.ticket.BuyTicketRequest;
import com.ultras.footbalticketsapp.controller.ticket.TicketResponse;
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

    @Autowired
    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @PostMapping("/add")
    public ResponseEntity<TicketResponse> buyTicket(@RequestBody BuyTicketRequest ticket){
        TicketResponse savedTicket = ticketService.buyTicket(ticket);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/tickets/" + savedTicket.getId()).toUriString());
        return ResponseEntity.created(uri).body(savedTicket);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable("ticketId") int ticketId){
        return ResponseEntity.ok().body(ticketService.getTicketById(ticketId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketResponse>> getTicketsByUserId(@PathVariable("userId") int userId){
        return ResponseEntity.ok().body(ticketService.getTicketsByUserId(userId));
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<Double> getAVGSaleOfTicketsPerTeam(@PathVariable("teamId") int teamId){
        return ResponseEntity.ok().body(ticketService.getAVGSaleOfTicketsPerTeam(teamId));
    }
}
