package com.ultras.footbalticketsapp.controller.user;

import com.ultras.footbalticketsapp.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private int id;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    //private List<Role> roles;
    private AccountType role;
}
