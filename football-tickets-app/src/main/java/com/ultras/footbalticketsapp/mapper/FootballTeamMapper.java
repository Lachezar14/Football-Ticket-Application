package com.ultras.footbalticketsapp.mapper;

import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamResponse;
import com.ultras.footbalticketsapp.controller.footballTeam.NewFootballTeamRequest;
import com.ultras.footbalticketsapp.model.FootballTeam;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FootballTeamMapper {

    FootballTeam newFootballTeamRequestToFootballTeam(NewFootballTeamRequest team);
    FootballTeam footballTeamResponseToFootballTeam(FootballTeamResponse team);
    FootballTeamResponse footballTeamToFootballTeamResponse(FootballTeam team);
    List<FootballTeamResponse> footballTeamsToFootballTeamsResponse(List<FootballTeam> teams);
}
