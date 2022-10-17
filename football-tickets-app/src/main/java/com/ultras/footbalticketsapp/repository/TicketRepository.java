package com.ultras.footbalticketsapp.repository;

import com.ultras.footbalticketsapp.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query(
            value = "SELECT * FROM tickets t WHERE t.buyer_id = ?1",
            nativeQuery = true)
    List<Ticket> findAllByUserId(int userId);
    //List<Ticket> findAllByMatchId(int matchId);

}
