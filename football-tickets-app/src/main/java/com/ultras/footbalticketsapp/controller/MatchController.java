package com.ultras.footbalticketsapp.controller;

import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.User;
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

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping("/add")
    public ResponseEntity<Match> saveMatch(@RequestBody Match match){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/add").toUriString());
        return ResponseEntity.created(uri).body(matchService.saveMatch(match));
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<Match> getMatch(@PathVariable("matchId") int matchId){
        return ResponseEntity.ok().body(matchService.getMatchById(matchId));
    }

    @GetMapping("/")
    public ResponseEntity<List<Match>> getAllMatches(){
        return ResponseEntity.ok().body(matchService.getAllMatches());
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
