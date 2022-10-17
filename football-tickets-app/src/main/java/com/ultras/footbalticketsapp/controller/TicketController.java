package com.ultras.footbalticketsapp.controller;

import com.ultras.footbalticketsapp.model.Ticket;
import com.ultras.footbalticketsapp.serviceInterface.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String saveTicket(@RequestBody Ticket ticket){
        ticketService.saveTicket(ticket);
        return "Ticket saved successfully";
    }

    @GetMapping("/{ticketId}")
    public Ticket getTicket(@PathVariable("ticketId") int ticketId){
        return ticketService.getTicketById(ticketId);
    }

    /*@GetMapping("/matchTickets")
    public List<Ticket> getTicketsByMatchId(@PathVariable("matchId") int matchId){
        return ticketService.getTicketsByMatchId(matchId);
    }*/

    @GetMapping("/user/{userId}")
    public List<Ticket> getTicketsByUserId(@PathVariable("userId") int userId){
        return ticketService.getTicketsByUserId(userId);
    }

    @PutMapping("/{ticketId}")
    public Ticket updateTicket(@PathVariable("ticketId") Ticket ticket){
        return ticketService.updateTicket(ticket);
    }

    @DeleteMapping("/{ticketId}")
    public String deleteTicket(@PathVariable("ticketId") int ticketId){
        ticketService.deleteTicket(ticketId);
        return "Ticket deleted successfully";
    }
}
