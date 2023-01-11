package com.ultras.footbalticketsapp.service;

import com.ultras.footbalticketsapp.controller.match.MatchMapper;
import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.repository.MatchRepository;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.matchMapper = matchMapper;
    }

    @Override
    public Match saveMatch(Match match) {
        if(matchRepository.findByHomeTeamAndAwayTeamAndDate(match.getHome_team().getId(), match.getAway_team().getId(), match.getDate()) != null){
            throw new RuntimeException("Match already exists");
        }
        if(match.getHome_team().getId() == match.getAway_team().getId()){
            throw new RuntimeException("Home team and away team cannot be the same");
        }
        matchRepository.save(match);
        return match;
    }

    @Override
    public Match getMatchById(int matchId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        return match;
    }

    @Override
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public int getNumberOfMatchesByTeam(int teamId) {
        return matchRepository.getNumberOfMatchesByTeam(teamId);
    }
    @Override
    public Match updateMatch(Match match) {
        Match toUpdate = matchRepository.findById(match.getId()).orElse(null);
        if(toUpdate == null){
            throw new RuntimeException("Match not found");
        }
        if(match.getHome_team().getId() == match.getAway_team().getId()){
            throw new RuntimeException("Home team and away team cannot be the same");
        }
        toUpdate.setHome_team(match.getHome_team());
        toUpdate.setAway_team(match.getAway_team());
        toUpdate.setDate(match.getDate());
        toUpdate.setTicket_number(match.getTicket_number());
        toUpdate.setTicket_price(match.getTicket_price());
        matchRepository.save(toUpdate);
        return toUpdate;
    }

    @Override
    public void deleteMatchById(int matchId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if(match == null){
            throw new RuntimeException("Match not found");
        }
        matchRepository.delete(match);
    }

    @Override
    public void TicketBought(int matchId, int ticketAmount) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if(match == null){
            throw new RuntimeException("Match not found");
        }
        if(match.getTicket_number() == 0){
            throw new RuntimeException("No tickets left");
        }
        match.setTicket_number(match.getTicket_number() - ticketAmount);
        matchRepository.save(match);
    }
}


