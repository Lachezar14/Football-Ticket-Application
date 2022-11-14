package com.ultras.footbalticketsapp.serviceInterface;

import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamResponse;
import com.ultras.footbalticketsapp.controller.footballTeam.NewFootballTeamRequest;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Stadium;

import java.util.List;

public interface FootballTeamService {
    FootballTeamResponse saveFootballTeam(NewFootballTeamRequest team);
    FootballTeamResponse getFootballTeamById(int id);
    List<FootballTeamResponse> getAllFootballTeams();
    FootballTeamResponse updateFootballTeam(FootballTeamResponse footballTeam);
    void deleteFootballTeam(int id);
    void updateStadium(Stadium stadium);
    //Stadium saveStadium(Stadium stadium);
    //Stadium getStadiumById(int id);
    //void deleteStadium(int id);
}
