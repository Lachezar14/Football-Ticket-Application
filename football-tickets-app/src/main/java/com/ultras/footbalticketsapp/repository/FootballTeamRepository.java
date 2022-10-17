package com.ultras.footbalticketsapp.repository;

import com.ultras.footbalticketsapp.model.FootballTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballTeamRepository extends JpaRepository<FootballTeam, Integer> {
}
