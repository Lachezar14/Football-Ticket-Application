package com.ultras.footbalticketsapp.serviceInterface;

import com.ultras.footbalticketsapp.controller.match.MatchResponse;
import com.ultras.footbalticketsapp.controller.match.NewMatchRequest;
import com.ultras.footbalticketsapp.model.Match;

import java.util.List;

public interface MatchService {
    MatchResponse saveMatch(NewMatchRequest match);
    MatchResponse getMatchById(int matchId);
    List<MatchResponse> getAllMatches();
    MatchResponse updateMatch(MatchResponse match);
    void deleteMatchById(int matchId);
    boolean TicketBought(int matchId);
}
