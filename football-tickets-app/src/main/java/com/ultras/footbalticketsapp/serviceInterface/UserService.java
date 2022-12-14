package com.ultras.footbalticketsapp.serviceInterface;

import com.ultras.footbalticketsapp.controller.user.NewPasswordRequest;
import com.ultras.footbalticketsapp.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface UserService {
    User registerUser(User user);
    void makeUserAdmin(User user);
    User getUserById(int userId);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    User updateUser(User updateUser);
    boolean updatePassword(NewPasswordRequest newPasswordRequest);
    void deleteUserById(int userId);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
