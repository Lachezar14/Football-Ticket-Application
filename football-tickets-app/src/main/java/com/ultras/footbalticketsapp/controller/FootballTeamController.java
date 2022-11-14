package com.ultras.footbalticketsapp.controller;

import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamResponse;
import com.ultras.footbalticketsapp.controller.footballTeam.NewFootballTeamRequest;
import com.ultras.footbalticketsapp.serviceInterface.FootballTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/teams")
public class FootballTeamController {

    private final FootballTeamService footballTeamService;

    @Autowired
    public FootballTeamController(FootballTeamService footballTeamService) {
        this.footballTeamService = footballTeamService;
    }

    @PostMapping("/add")
    public ResponseEntity<FootballTeamResponse> saveFootballTeam(@RequestBody NewFootballTeamRequest newFootballTeamRequest) {
        FootballTeamResponse savedTeam = footballTeamService.saveFootballTeam(newFootballTeamRequest);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/teams/" + savedTeam.getId()).toUriString());
        return ResponseEntity.created(uri).body(savedTeam);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<FootballTeamResponse> getFootballTeamById(@PathVariable("teamId") int teamId){
        return ResponseEntity.ok().body(footballTeamService.getFootballTeamById(teamId));
    }

    @GetMapping("/")
    public ResponseEntity<List<FootballTeamResponse>> getAllFootballTeams(){
        return ResponseEntity.ok().body(footballTeamService.getAllFootballTeams());
    }

    @PutMapping("/update")
    public ResponseEntity<FootballTeamResponse> updateFootballTeam(@RequestBody FootballTeamResponse team){
        return ResponseEntity.ok().body(footballTeamService.updateFootballTeam(team));
    }

    @DeleteMapping("/{teamId}")
    public String deleteFootballTeam(@PathVariable("teamId") int teamId){
        footballTeamService.deleteFootballTeam(teamId);
        return "Football team deleted successfully";
    }

    //TODO remove because is in saveTeam method
//    @PostMapping("/stadium")
//    public String saveStadium(Stadium stadium){
//        footballTeamService.saveStadium(stadium);
//        return "Stadium saved successfully";
//    }

    //TODO remove because is not used
//    @GetMapping("/stadium/{stadiumId}")
//    public Stadium getStadium(@PathVariable("stadiumId") int stadiumId){
//        return footballTeamService.getStadiumById(stadiumId);
//    }

//    @DeleteMapping("/stadium/{stadiumId}")
//    public String deleteStadium(@PathVariable("stadiumId") int stadiumId){
//        footballTeamService.deleteStadium(stadiumId);
//        return "Stadium deleted successfully";
//    }
}
