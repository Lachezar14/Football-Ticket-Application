package com.ultras.footbalticketsapp.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    private String password;

    @OneToMany
//    @JoinColumn(name = "ticket_id", referencedColumnName = "id")
    List<Ticket> tickets = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

}
