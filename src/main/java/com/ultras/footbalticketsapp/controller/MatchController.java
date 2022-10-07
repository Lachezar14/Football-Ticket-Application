package com.ultras.footbalticketsapp.controller;

import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.User;
import com.ultras.footbalticketsapp.serviceInterface.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/")
    public String addMatch(@RequestBody Match match){
        matchService.saveMatch(match);
        return "Match added successfully";
    }

    @GetMapping("/{matchId}")
    public Match getMatch(@PathVariable("matchId") int matchId){
        return matchService.getMatchById(matchId);
    }

    @GetMapping("/")
    public List<Match> getAllMatches(){
        return matchService.getAllMatches();
    }

    @DeleteMapping("/{matchId}")
    public String deleteMatch(@PathVariable("matchId") int matchId){
        matchService.deleteMatchById(matchId);
        return "Match deleted successfully";
    }

    @PutMapping("/{matchId}")
    public Match updateMatch(@PathVariable("matchId") Match match){
        return matchService.updateMatch(match);
    }
}
