package com.ultras.footbalticketsapp.controller;

import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamResponse;
import com.ultras.footbalticketsapp.controller.footballTeam.NewFootballTeamRequest;
import com.ultras.footbalticketsapp.mapper.FootballTeamMapper;
import com.ultras.footbalticketsapp.model.FootballTeam;
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
    private final FootballTeamMapper footballTeamMapper;

    @Autowired
    public FootballTeamController(FootballTeamService footballTeamService, FootballTeamMapper footballTeamMapper) {
        this.footballTeamService = footballTeamService;
        this.footballTeamMapper = footballTeamMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<FootballTeamResponse> saveFootballTeam(@RequestBody NewFootballTeamRequest newFootballTeamRequest) {
        FootballTeam footballTeam = footballTeamMapper.newFootballTeamRequestToFootballTeam(newFootballTeamRequest);
        FootballTeamResponse savedTeam = footballTeamMapper.footballTeamToFootballTeamResponse(footballTeamService.saveFootballTeam(footballTeam));
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/teams/" + savedTeam.getId()).toUriString());
        return ResponseEntity.created(uri).body(savedTeam);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<FootballTeamResponse> getFootballTeamById(@PathVariable("teamId") int teamId){
        return ResponseEntity.ok().body(footballTeamMapper.footballTeamToFootballTeamResponse(footballTeamService.getFootballTeamById(teamId)));
    }

    @GetMapping("/")
    public ResponseEntity<List<FootballTeamResponse>> getAllFootballTeams(){
        return ResponseEntity.ok().body(footballTeamMapper.footballTeamsToFootballTeamsResponse(footballTeamService.getAllFootballTeams()));
    }

    @PutMapping("/update")
    public ResponseEntity<FootballTeamResponse> updateFootballTeam(@RequestBody FootballTeamResponse updateTeam){
        FootballTeam footballTeam = footballTeamMapper.footballTeamResponseToFootballTeam(updateTeam);
        return ResponseEntity.ok().body(footballTeamMapper.footballTeamToFootballTeamResponse(footballTeamService.updateFootballTeam(footballTeam)));
    }

    @DeleteMapping("/{teamId}")
    public String deleteFootballTeam(@PathVariable("teamId") int teamId){
        footballTeamService.deleteFootballTeam(teamId);
        return "Football team deleted successfully";
    }
}
