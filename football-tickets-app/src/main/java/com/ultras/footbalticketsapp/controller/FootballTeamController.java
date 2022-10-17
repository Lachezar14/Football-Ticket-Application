package com.ultras.footbalticketsapp.controller;

import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.serviceInterface.FootballTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String saveFootballTeam(@RequestBody FootballTeam footballTeam){
        footballTeamService.saveFootballTeam(footballTeam);
        return "Football team saved successfully";
    }

    @GetMapping("/{teamId}")
    public FootballTeam getFootballTeam(@PathVariable("teamId") int teamId){
        return footballTeamService.getFootballTeamById(teamId);
    }

    @GetMapping("/")
    public List<FootballTeam> getAllFootballTeams(){
        return footballTeamService.getAllFootballTeams();
    }

    @PutMapping("/{teamId}")
    public FootballTeam updateFootballTeam(@PathVariable("teamId") FootballTeam team){
        return footballTeamService.updateFootballTeam(team);
    }

    @DeleteMapping("/{teamId}")
    public String deleteFootballTeam(@PathVariable("teamId") int teamId){
        footballTeamService.deleteFootballTeam(teamId);
        return "Football team deleted successfully";
    }

    @PostMapping("/stadium")
    public String saveStadium(Stadium stadium){
        footballTeamService.saveStadium(stadium);
        return "Stadium saved successfully";
    }

    @GetMapping("/stadium/{stadiumId}")
    public Stadium getStadium(@PathVariable("stadiumId") int stadiumId){
        return footballTeamService.getStadiumById(stadiumId);
    }

    @DeleteMapping("/stadium/{stadiumId}")
    public String deleteStadium(@PathVariable("stadiumId") int stadiumId){
        footballTeamService.deleteStadium(stadiumId);
        return "Stadium deleted successfully";
    }
}
