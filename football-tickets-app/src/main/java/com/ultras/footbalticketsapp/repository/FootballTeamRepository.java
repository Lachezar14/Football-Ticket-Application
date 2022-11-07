package com.ultras.footbalticketsapp.repository;

import com.ultras.footbalticketsapp.model.FootballTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface FootballTeamRepository extends JpaRepository<FootballTeam, Integer> {
    FootballTeam findByName(String name);

    @Modifying
    @Query(value = "DELETE FROM football_teams WHERE id = ?1",
            nativeQuery = true)
    void deleteTeam(int id);
}
