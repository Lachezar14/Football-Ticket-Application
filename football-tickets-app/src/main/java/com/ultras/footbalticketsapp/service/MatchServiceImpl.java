package com.ultras.footbalticketsapp.service;

import com.ultras.footbalticketsapp.controller.match.MatchResponse;
import com.ultras.footbalticketsapp.controller.match.NewMatchRequest;
import com.ultras.footbalticketsapp.mapper.MatchMapper;
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
    public MatchResponse saveMatch(NewMatchRequest newMatchRequest) {
        Match match = matchMapper.newMatchRequestToMatch(newMatchRequest);
        //TODO throws query exception check query implementation
//        if(matchRepository.findByHomeTeamAndAwayTeamAndDate(match.getHome_team().getId(), match.getAway_team().getId(), match.getDate()) != null){
//            throw new IllegalStateException("Match already exists");
//        }
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
    public MatchResponse updateMatch(MatchResponse match) {
        Match toUpdate = matchRepository.findById(match.getId()).orElse(null);
        Match updated = matchMapper.matchResponseToMatch(match);
        if(toUpdate == null){
            throw new IllegalStateException("Match not found");
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
            throw new IllegalStateException("Match not found");
        }
        matchRepository.delete(match);
    }

    @Override
    public boolean TicketBought(int matchId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if(match == null){
            throw new IllegalStateException("Match not found");
        }
        if(match.getTicket_number() == 0){
            throw new IllegalStateException("No tickets left");
        }
        match.setTicket_number(match.getTicket_number() - 1);
        matchRepository.save(match);
        return true;
    }
}


