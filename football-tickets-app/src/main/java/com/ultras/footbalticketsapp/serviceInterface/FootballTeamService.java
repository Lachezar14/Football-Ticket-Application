package com.ultras.footbalticketsapp.serviceInterface;

import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Stadium;

import java.util.List;

public interface FootballTeamService {
    FootballTeam saveFootballTeam(FootballTeam team);
    FootballTeam getFootballTeamById(int id);
    List<FootballTeam> getAllFootballTeams();
    FootballTeam updateFootballTeam(FootballTeam footballTeam);
    void deleteFootballTeam(int id);
    void updateStadium(Stadium stadium);
}
