package com.ultras.footbalticketsapp.serviceInterface;

import com.ultras.footbalticketsapp.dto.user.NewUserDTO;
import com.ultras.footbalticketsapp.dto.user.UserDTO;
import com.ultras.footbalticketsapp.model.Role;
import com.ultras.footbalticketsapp.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {
    User saveUser(NewUserDTO user);
    Role saveRole(Role role);
    void addRoleToUser(User user, String roleName);
    User getUserByEmail(String email);
    User getUserById(int userId);
    List<User> getAllUsers();
    void deleteUserById(int userId);
    User updateUser(User user);
    UserDTO getUserDTO(String email);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
