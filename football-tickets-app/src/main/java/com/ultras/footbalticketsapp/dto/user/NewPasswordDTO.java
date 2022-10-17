package com.ultras.footbalticketsapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewPasswordDTO {
    private int id;
    private String current_password;
    private String new_password;
}
