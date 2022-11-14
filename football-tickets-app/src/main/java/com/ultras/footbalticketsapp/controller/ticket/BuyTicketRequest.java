package com.ultras.footbalticketsapp.controller.ticket;

import com.ultras.footbalticketsapp.model.Match;
import com.ultras.footbalticketsapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyTicketRequest {
    private int ticketAmount;
    private Match match;
    private User buyer;
    private double price;
}
