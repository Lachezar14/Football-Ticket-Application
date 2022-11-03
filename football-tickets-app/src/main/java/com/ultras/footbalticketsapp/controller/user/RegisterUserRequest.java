package com.ultras.footbalticketsapp.controller.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {
    private String first_name;
    private String last_name;
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "Phone number must be 10 digits")
    private String phone_number;
    @Email(message = "Email should be valid")
    private String email;
    private String password;
    private String roleName;
}
