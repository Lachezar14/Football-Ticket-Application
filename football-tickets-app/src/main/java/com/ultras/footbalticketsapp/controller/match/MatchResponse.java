package com.ultras.footbalticketsapp.controller.match;

import com.ultras.footbalticketsapp.model.FootballTeam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponse {
    private int Id;
    private FootballTeam home_team;
    private FootballTeam away_team;
    private Date date;
    private int ticket_number;
    private double ticket_price;
}
