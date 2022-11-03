package com.ultras.footbalticketsapp.serviceInterface;

import com.ultras.footbalticketsapp.controller.user.NewPasswordRequest;
import com.ultras.footbalticketsapp.controller.user.RegisterUserRequest;
import com.ultras.footbalticketsapp.controller.user.UpdateUserRequest;
import com.ultras.footbalticketsapp.controller.user.UserDTO;
import com.ultras.footbalticketsapp.model.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDTO registerUser(RegisterUserRequest user);
    Role saveRole(Role role);
    void makeUserAdmin(UserDTO user);
    UserDTO getUserById(int userId);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    UserDTO updateUser(UpdateUserRequest updateUserRequest);
    boolean updatePassword(NewPasswordRequest newPasswordRequest);
    void deleteUserById(int userId);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
