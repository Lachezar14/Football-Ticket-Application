package com.ultras.footbalticketsapp.controller;

import com.ultras.footbalticketsapp.dto.user.NewPasswordDTO;
import com.ultras.footbalticketsapp.dto.user.NewUserDTO;
import com.ultras.footbalticketsapp.dto.user.UserDTO;
import com.ultras.footbalticketsapp.model.User;
import com.ultras.footbalticketsapp.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
//    public ResponseEntity<RegisterUserResponse> saveUser(@RequestBody RegisterUserRequest user) {
    public ResponseEntity<User> saveUser(@Valid @RequestBody NewUserDTO user) {
        User savedUser = userService.saveUser(user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/" + savedUser.getId()).toUriString());
        return ResponseEntity.created(uri).body(savedUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") int userId){
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable(value = "email") String email){
        return ResponseEntity.ok().body(userService.getUserByEmail(email));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") int userId){
        userService.deleteUserById(userId);
        return "User deleted successfully";
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable("userId") UserDTO userDTO){
        return userService.updateUser(userDTO);
    }

    @PutMapping("/new-password")
    public boolean updatePassword(@RequestBody NewPasswordDTO newPasswordDTO){
        return userService.updatePassword(newPasswordDTO);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
    }


}
