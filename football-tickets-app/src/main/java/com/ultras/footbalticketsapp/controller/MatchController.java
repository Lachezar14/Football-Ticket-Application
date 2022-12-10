package com.ultras.footbalticketsapp.controller;

import com.ultras.footbalticketsapp.controller.match.MatchResponse;
import com.ultras.footbalticketsapp.controller.match.NewMatchRequest;
import com.ultras.footbalticketsapp.mapper.MatchMapper;
import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;
    private final MatchMapper matchMapper;

    @Autowired
    public MatchController(MatchService matchService, MatchMapper matchMapper) {
        this.matchService = matchService;
        this.matchMapper = matchMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<MatchResponse> saveMatch(@RequestBody NewMatchRequest match) {
        Match newMatch = matchMapper.newMatchRequestToMatch(match);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/matches/").toUriString());
        return ResponseEntity.created(uri).body(matchMapper.matchToMatchResponse(matchService.saveMatch(newMatch)));
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<MatchResponse> getMatchById(@PathVariable("matchId") int matchId){
        return ResponseEntity.ok().body(matchMapper.matchToMatchResponse(matchService.getMatchById(matchId)));
    }

    @GetMapping("/")
    public ResponseEntity<List<MatchResponse>> getAllMatches(){
        return ResponseEntity.ok().body(matchMapper.matchesToMatchesResponse(matchService.getAllMatches()));
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<Integer> getNumberOfMatchesByTeam(@PathVariable("teamId") int teamId){
        return ResponseEntity.ok().body(matchService.getNumberOfMatchesByTeam(teamId));
    }

    @PutMapping("/{matchId}")
    public ResponseEntity<MatchResponse> updateMatch(@PathVariable("matchId") int id, @RequestBody MatchResponse match){
        Match updatedMatch = matchMapper.matchResponseToMatch(match);
        return ResponseEntity.ok().body(matchMapper.matchToMatchResponse(matchService.updateMatch(updatedMatch)));
    }

    @DeleteMapping("/{matchId}")
    public String deleteMatch(@PathVariable("matchId") int matchId){
        matchService.deleteMatchById(matchId);
        return "Match deleted successfully";
    }
}
