package com.ultras.footbalticketsapp.controller.footballTeam;

import com.ultras.footbalticketsapp.model.Stadium;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewFootballTeamRequest {
    private String name;
    private Stadium stadium;
}
