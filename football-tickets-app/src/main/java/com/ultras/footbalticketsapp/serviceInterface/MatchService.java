package com.ultras.footbalticketsapp.serviceInterface;

import com.ultras.footbalticketsapp.model.Match;

import java.util.List;

public interface MatchService {
    public Match saveMatch(Match match);
    public Match getMatchById(int matchId);
    public void deleteMatchById(int matchId);
    public Match updateMatch(Match match);
    public List<Match> getAllMatches();
}
