package com.ultras.footbalticketsapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "football_teams")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FootballTeam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String name;

    @OneToOne
    @JoinColumn(name = "stadium_id", referencedColumnName = "id")
    //@Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private Stadium stadium;
}
