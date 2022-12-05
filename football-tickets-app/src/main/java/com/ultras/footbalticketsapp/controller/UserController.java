package com.ultras.footbalticketsapp.controller;

import com.ultras.footbalticketsapp.controller.user.NewPasswordRequest;
import com.ultras.footbalticketsapp.controller.user.RegisterUserRequest;
import com.ultras.footbalticketsapp.controller.user.UpdateUserRequest;
import com.ultras.footbalticketsapp.controller.user.UserDTO;
import com.ultras.footbalticketsapp.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody RegisterUserRequest user) {
        UserDTO savedUser = userService.registerUser(user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/" + savedUser.getId()).toUriString());
        return ResponseEntity.created(uri).body(savedUser);
    }

    @PutMapping("/admin")
    public void makeUserAdmin(@RequestBody UserDTO user) {
        userService.makeUserAdmin(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") int userId){
        return ResponseEntity.ok().body(userService.getUserById(userId));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable(value = "email") String email){
        Object loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(loggedUserEmail.equals(email)){
            return ResponseEntity.ok().body(userService.getUserByEmail(email));
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    //TODO this workd do for all controllers when renaming the URIs
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest, @PathVariable("userId") int userId){
        Object loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(loggedUserEmail.equals(updateUserRequest.getEmail())){
            return ResponseEntity.ok().body(userService.updateUser(updateUserRequest));
        }
        return ResponseEntity.status(401).build();
    }

    @PutMapping("/new-password")
    public boolean updatePassword(@RequestBody NewPasswordRequest newPasswordRequest){
        return userService.updatePassword(newPasswordRequest);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable("userId") int userId){
        userService.deleteUserById(userId);
        return "User deleted successfully";
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userService.refreshToken(request, response);
    }
}
