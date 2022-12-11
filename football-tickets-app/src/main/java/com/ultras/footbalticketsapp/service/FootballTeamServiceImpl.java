package com.ultras.footbalticketsapp.service;

import com.ultras.footbalticketsapp.controller.footballTeam.FootballTeamMapper;
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
    public FootballTeam saveFootballTeam(FootballTeam footballTeam) {
        if(footballTeamRepository.findByName(footballTeam.getName()) != null){
            throw new RuntimeException("Team already exists");
        }
        Stadium stadium = footballTeam.getStadium();
        stadiumRepository.save(stadium);
        footballTeamRepository.save(footballTeam);
        return footballTeam;
    }

    @Override
    public FootballTeam getFootballTeamById(int id) {
        FootballTeam team = footballTeamRepository.findById(id).orElse(null);
        return team;
    }

    @Override
    public List<FootballTeam> getAllFootballTeams() {
        return footballTeamRepository.findAll();
    }

    @Override
    public FootballTeam updateFootballTeam(FootballTeam footballTeam) {
        FootballTeam toUpdate = footballTeamRepository.findById(footballTeam.getId()).orElse(null);
        if (toUpdate == null) {
            throw new RuntimeException("Team not found");
        }
        toUpdate.setName(footballTeam.getName());
        updateStadium(footballTeam.getStadium());
        footballTeamRepository.save(toUpdate);
        return toUpdate;
    }

    @Override
    public void deleteFootballTeam(int id) {
        FootballTeam footballTeam = footballTeamRepository.findById(id).orElse(null);
        if (footballTeam == null) {
            throw new RuntimeException("Team not found");
        }
        footballTeamRepository.delete(footballTeam);
        stadiumRepository.delete(footballTeam.getStadium());
    }

    public void updateStadium(Stadium stadium){
        Stadium toUpdate = stadiumRepository.findById(stadium.getId()).orElse(null);
        if (toUpdate == null) {
            throw new RuntimeException("Stadium not found");
        }
        toUpdate.setName(stadium.getName());
        toUpdate.setCapacity(stadium.getCapacity());
        stadiumRepository.save(toUpdate);
    }
}
