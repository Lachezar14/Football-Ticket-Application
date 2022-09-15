package com.ultras.footbalticketsapp.serviceInterface;

import com.ultras.footbalticketsapp.model.User;

import java.util.List;

public interface UserService {
    public User saveUser(User user);
    public List<User> getAllUsers();
}
