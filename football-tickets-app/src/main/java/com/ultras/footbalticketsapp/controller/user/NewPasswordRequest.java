package com.ultras.footbalticketsapp.controller.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordRequest {
    private int id;
    private String current_password;
    private String new_password;
}
