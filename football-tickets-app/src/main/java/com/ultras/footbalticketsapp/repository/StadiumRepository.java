package com.ultras.footbalticketsapp.repository;

import com.ultras.footbalticketsapp.model.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Integer> {
}
