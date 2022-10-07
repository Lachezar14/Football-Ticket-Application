package com.ultras.footbalticketsapp.repository;

import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {
}
