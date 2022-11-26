package com.ultras.footbalticketsapp.service;

import com.ultras.footbalticketsapp.controller.match.MatchResponse;
import com.ultras.footbalticketsapp.controller.ticket.BuyTicketRequest;
import com.ultras.footbalticketsapp.controller.ticket.TicketResponse;
import com.ultras.footbalticketsapp.mapper.TicketMapper;
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
    public TicketResponse buyTicket(BuyTicketRequest buyTicketRequest) {
        Ticket ticket = ticketMapper.buyTicketRequestToTicket(buyTicketRequest);
        MatchResponse match = matchService.getMatchById(ticket.getMatch().getId());
        if(match.getTicket_number() < buyTicketRequest.getTicketAmount()){
            throw new RuntimeException("Not enough tickets available");
        }
        matchService.TicketBought(match.getId(), buyTicketRequest.getTicketAmount());
        for (int i = 0; i < buyTicketRequest.getTicketAmount(); i++) {
            Ticket saveTicket = new Ticket();
            saveTicket.setBuyer(ticket.getBuyer());
            saveTicket.setMatch(ticket.getMatch());
            saveTicket.setPrice(ticket.getPrice());
            ticketRepository.save(saveTicket);
        }
        return ticketMapper.ticketToTicketResponse(ticket);

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

    @Override
    public int countAllByTeamId(int teamId){
        return ticketRepository.countAllByTeamId(teamId);
    }

    @Override
    public double getAVGSaleOfTicketsPerTeam(int teamId){
        return ticketRepository.getAVGSaleOfTicketsPerTeam(teamId);
    }

    //TODO remove because ticket data should not be changed
//
//    @Override
//    public TicketResponse updateTicket(TicketResponse ticket) {
//        Ticket updatedTicket = ticketMapper.ticketResponseToTicket(ticket);
//        Ticket ticketToUpdate = ticketRepository.findById(ticket.getId()).orElse(null);
//        if(ticketToUpdate == null){
//            throw new IllegalStateException("Ticket not found");
//        }
//        ticketToUpdate.setMatch(updatedTicket.getMatch());
//        ticketToUpdate.setBuyer(updatedTicket.getBuyer());
//        ticketToUpdate.setPrice(updatedTicket.getPrice());
//        ticketRepository.save(ticketToUpdate);
//        return ticketMapper.ticketToTicketResponse(ticketToUpdate);
//    }

    //TODO should be removed because data of every bought ticket should be kept
//    @Override
//    public void deleteTicket(int id) {
//        Ticket ticket = ticketRepository.findById(id).orElse(null);
//        if(ticket == null){
//            throw new IllegalStateException("Ticket not found");
//        }
//        ticketRepository.delete(ticket);
//    }
}
