package com.ultras.footbalticketsapp.serviceInterface;

import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Stadium;

import java.util.List;

public interface FootballTeamService {
    FootballTeam saveFootballTeam(FootballTeam footballTeam);
    FootballTeam getFootballTeamById(int id);
    List<FootballTeam> getAllFootballTeams();
    FootballTeam updateFootballTeam(FootballTeam footballTeam);
    void deleteFootballTeam(int id);
    Stadium saveStadium(Stadium stadium);
    Stadium getStadiumById(int id);
    void deleteStadium(int id);
}
