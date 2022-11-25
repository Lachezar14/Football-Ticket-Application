package com.ultras.footbalticketsapp.repository;

import com.ultras.footbalticketsapp.model.FootballTeam;
import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
    @Query(
            value = "SELECT * FROM matches m WHERE m.home_team_id = ?1 AND m.away_team_id = ?2 AND m.date = ?3",
            nativeQuery = true)
    Match findByHomeTeamAndAwayTeamAndDate(int homeTeamId, int awayTeamId, Date date);

    @Query(
            value = "SELECT COUNT(*) FROM `matches` WHERE (away_team_id = ?1) OR (home_team_id = ?1);",
            nativeQuery = true)
    int getNumberOfMatchesByTeam(int teamId);
}
