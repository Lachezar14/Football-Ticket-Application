package com.ultras.footbalticketsapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "matches")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @OneToOne
    @JoinColumn(name = "home_team_id", referencedColumnName = "id")
    private FootballTeam home_team;

    @OneToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id")
    private FootballTeam away_team;
    private Date date;
    private int ticket_number;
}
