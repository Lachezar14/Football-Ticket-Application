package com.ultras.footbalticketsapp.service;

import com.ultras.footbalticketsapp.controller.match.MatchResponse;
import com.ultras.footbalticketsapp.controller.match.NewMatchRequest;
import com.ultras.footbalticketsapp.mapper.MatchMapper;
import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.repository.MatchRepository;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public MatchResponse saveMatch(NewMatchRequest newMatchRequest) {
        Match match = matchMapper.newMatchRequestToMatch(newMatchRequest);
        if(matchRepository.findByHomeTeamAndAwayTeamAndDate(match.getHome_team().getId(), match.getAway_team().getId(), match.getDate()) != null){
            throw new RuntimeException("Match already exists");
        }
        //TODO add unit test for this
        if(newMatchRequest.getHome_team().getName() == newMatchRequest.getAway_team().getName()){
            throw new RuntimeException("Home team and away team cannot be the same");
        }
        matchRepository.save(match);
        return matchMapper.matchToMatchResponse(match);
    }

    @Override
    public MatchResponse getMatchById(int matchId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        return matchMapper.matchToMatchResponse(match);
    }

    @Override
    public List<MatchResponse> getAllMatches() {
        return matchMapper.matchesToMatchesResponse(matchRepository.findAll());
    }

    @Override
    public int getNumberOfMatchesByTeam(int teamId) {
        return matchRepository.getNumberOfMatchesByTeam(teamId);
    }
    @Override
    public MatchResponse updateMatch(MatchResponse match) {
        Match toUpdate = matchRepository.findById(match.getId()).orElse(null);
        Match updated = matchMapper.matchResponseToMatch(match);
        if(toUpdate == null){
            throw new RuntimeException("Match not found");
        }
        //TODO add unit test for this
        if(match.getHome_team().getName() == match.getAway_team().getName()){
            throw new RuntimeException("Home team and away team cannot be the same");
        }
        toUpdate.setHome_team(updated.getHome_team());
        toUpdate.setAway_team(updated.getAway_team());
        toUpdate.setDate(updated.getDate());
        toUpdate.setTicket_number(updated.getTicket_number());
        toUpdate.setTicket_price(updated.getTicket_price());
        matchRepository.save(toUpdate);
        return matchMapper.matchToMatchResponse(toUpdate);
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


