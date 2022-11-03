package com.ultras.footbalticketsapp.controller.user;

import com.ultras.footbalticketsapp.model.AccountType;
import com.ultras.footbalticketsapp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

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
