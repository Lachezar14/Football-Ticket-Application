package com.ultras.footbalticketsapp.service;

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

    public FootballTeamServiceImpl(FootballTeamRepository footballTeamRepository, StadiumRepository stadiumRepository) {
        this.footballTeamRepository = footballTeamRepository;
        this.stadiumRepository = stadiumRepository;
    }

    @Override
    public FootballTeam saveFootballTeam(FootballTeam footballTeam) {
        Stadium stadium = footballTeam.getStadium();
        stadiumRepository.save(stadium);
        return footballTeamRepository.save(footballTeam);
    }

    @Override
    public FootballTeam getFootballTeamById(int id) {
        return footballTeamRepository.findById(id).orElse(null);
    }

    @Override
    public List<FootballTeam> getAllFootballTeams() {
        return footballTeamRepository.findAll();
    }

    @Override
    public FootballTeam updateFootballTeam(FootballTeam footballTeam) {
        return null;
    }

    @Override
    public void deleteFootballTeam(int id) {
        FootballTeam footballTeam = footballTeamRepository.findById(id).orElse(null);
        deleteStadium(footballTeam.getStadium().getId());
        footballTeamRepository.delete(footballTeam);
    }

    @Override
    public Stadium saveStadium(Stadium stadium) {
        return stadiumRepository.save(stadium);
    }

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
