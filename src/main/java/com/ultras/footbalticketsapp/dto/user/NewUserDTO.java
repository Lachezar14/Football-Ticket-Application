package com.ultras.footbalticketsapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.mapstruct.Mapper;

import javax.persistence.Column;

@Data
@AllArgsConstructor
public class NewUserDTO {
    private String first_name;
    private String last_name;
    private String phone_number;
    private String email;
    private String password;
    private String roleName;
}
