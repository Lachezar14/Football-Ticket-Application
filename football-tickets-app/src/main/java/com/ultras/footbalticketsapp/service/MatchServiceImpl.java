package com.ultras.footbalticketsapp.service;

import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.repository.MatchRepository;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public Match saveMatch(Match match) {
        return matchRepository.save(match);
    }

    @Override
    public Match getMatchById(int matchId) {
        return matchRepository.findById(matchId).orElse(null);
    }

    @Override
    public void deleteMatchById(int matchId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        matchRepository.delete(match);
    }

    @Override
    public Match updateMatch(Match match) {
        Match match1 = matchRepository.findById(match.getId()).orElse(null);
        match1.setHome_team(match.getHome_team());
        match1.setAway_team(match.getAway_team());
        match1.setDate(match.getDate());
        //match1.setTicket_number(match.getTicket_number());
        return matchRepository.save(match1);
    }

    @Override
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }
}


