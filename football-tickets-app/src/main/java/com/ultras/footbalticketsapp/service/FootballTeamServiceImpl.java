package com.ultras.footbalticketsapp.service;

import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamResponse;
import com.ultras.footbalticketsapp.controller.footballTeam.NewFootballTeamRequest;
import com.ultras.footbalticketsapp.mapper.FootballTeamMapper;
import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Stadium;
import com.ultras.footbalticketsapp.repository.FootballTeamRepository;
import com.ultras.footbalticketsapp.repository.StadiumRepository;
import com.ultras.footbalticketsapp.serviceInterface.FootballTeamService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FootballTeamServiceImpl implements FootballTeamService {

    private final FootballTeamRepository footballTeamRepository;
    private final StadiumRepository stadiumRepository;
    private final FootballTeamMapper footballTeamMapper;

    public FootballTeamServiceImpl(FootballTeamRepository footballTeamRepository, StadiumRepository stadiumRepository, FootballTeamMapper footballTeamMapper) {
        this.footballTeamRepository = footballTeamRepository;
        this.stadiumRepository = stadiumRepository;
        this.footballTeamMapper = footballTeamMapper;
    }

    @Override
    public FootballTeamResponse saveFootballTeam(NewFootballTeamRequest newFootballTeamRequest) {
        FootballTeam footballTeam = footballTeamMapper.newFootballTeamRequestToFootballTeam(newFootballTeamRequest);
        if(footballTeamRepository.findByName(footballTeam.getName()) != null){
            throw new IllegalStateException("Team already exists");
        }
        Stadium stadium = footballTeam.getStadium();
        stadiumRepository.save(stadium);
        footballTeamRepository.save(footballTeam);
        return footballTeamMapper.footballTeamToFootballTeamResponse(footballTeam);
    }

    @Override
    public FootballTeamResponse getFootballTeamById(int id) {
        FootballTeam team = footballTeamRepository.findById(id).orElse(null);
        return footballTeamMapper.footballTeamToFootballTeamResponse(team);
    }

    @Override
    public List<FootballTeamResponse> getAllFootballTeams() {
        return footballTeamMapper.footballTeamsToFootballTeamsResponse(footballTeamRepository.findAll());
    }

    @Override
    public FootballTeamResponse updateFootballTeam(FootballTeamResponse footballTeam) {
        FootballTeam toUpdate = footballTeamRepository.findById(footballTeam.getId()).orElse(null);
        FootballTeam updated = footballTeamMapper.footballTeamResponseToFootballTeam(footballTeam);
        if (toUpdate == null) {
            throw new IllegalStateException("Team not found");
        }
        toUpdate.setName(updated.getName());
        toUpdate.setStadium(updated.getStadium());
        footballTeamRepository.save(toUpdate);
        return footballTeamMapper.footballTeamToFootballTeamResponse(toUpdate);
    }

    @Override
    public void deleteFootballTeam(int id) {
        FootballTeam footballTeam = footballTeamRepository.findById(id).orElse(null);
        if (footballTeam == null) {
            throw new IllegalStateException("Team not found");
        }
        deleteStadium(footballTeam.getStadium().getId());
        footballTeamRepository.delete(footballTeam);
    }

    //TODO remove because it is in the saveTeam method
    @Override
    public Stadium saveStadium(Stadium stadium) {
        return stadiumRepository.save(stadium);
    }

    //TODO remove because it is not used
    @Override
    public Stadium getStadiumById(int id) {
        return stadiumRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteStadium(int id) {
        Stadium stadium = stadiumRepository.findById(id).orElse(null);
        stadiumRepository.delete(stadium);
    }
}
