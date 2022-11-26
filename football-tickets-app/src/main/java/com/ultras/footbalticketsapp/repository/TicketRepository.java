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

    @Query(
            value = "SELECT COUNT(*) FROM `tickets` INNER JOIN `matches` ON tickets.match_id = matches.id WHERE (matches.away_team_id = ?1) OR (matches.home_team_id = ?1);",
            nativeQuery = true)
    int countAllByTeamId(int teamId);

    @Query(
            value = "SELECT (SELECT COUNT(*) \n" +
                    "        FROM `tickets` \n" +
                    "        INNER JOIN `matches` \n" +
                    "        ON tickets.match_id = matches.id \n" +
                    "        WHERE (matches.away_team_id = ?1) OR (matches.home_team_id = ?1)) / \n" +
                    "        (SELECT COUNT(*) \n" +
                    "         FROM `matches` \n" +
                    "         WHERE (away_team_id = ?1) OR (home_team_id = ?1))",
            nativeQuery = true)
    double getAVGSaleOfTicketsPerTeam(int teamId);


}
