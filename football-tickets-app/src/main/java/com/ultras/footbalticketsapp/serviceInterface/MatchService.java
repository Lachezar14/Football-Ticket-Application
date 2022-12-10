package com.ultras.footbalticketsapp.serviceInterface;

import com.ultras.footbalticketsapp.model.Match;

import java.util.List;

public interface MatchService {
    Match saveMatch(Match match);
    Match getMatchById(int matchId);
    List<Match> getAllMatches();
    int getNumberOfMatchesByTeam(int teamId);
    Match updateMatch(Match match);
    void deleteMatchById(int matchId);
    void TicketBought(int matchId, int ticketAmount);
}
