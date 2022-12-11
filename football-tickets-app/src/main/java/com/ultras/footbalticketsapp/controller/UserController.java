package com.ultras.footbalticketsapp.controller;

import com.ultras.footbalticketsapp.controller.user.*;
import com.ultras.footbalticketsapp.controller.user.UserResponse;
import com.ultras.footbalticketsapp.model.User;
import com.ultras.footbalticketsapp.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterUserRequest newUser) {
        User user = userMapper.registerUserRequestToUser(newUser);
        UserResponse savedUser = userMapper.userToUserDTO(userService.registerUser(user));
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/" + savedUser.getId()).toUriString());
        return ResponseEntity.created(uri).body(savedUser);
    }

    @PutMapping("/admin")
    public void makeUserAdmin(@RequestBody UserResponse userResponse) {
        User user = userMapper.userDTOtoUser(userResponse);
        userService.makeUserAdmin(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("userId") int userId){
        return ResponseEntity.ok().body(userMapper.userToUserDTO(userService.getUserById(userId)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable(value = "email") String email){
        Object loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(loggedUserEmail.equals(email)){
            return ResponseEntity.ok().body(userMapper.userToUserDTO(userService.getUserByEmail(email)));
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok().body(userMapper.usersToUsersDTO(userService.getAllUsers()));
    }

    //TODO this works, do for all controllers when renaming the URIs
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest, @PathVariable("userId") int userId){
        User user = userMapper.updateUserRequestToUser(updateUserRequest);
        return ResponseEntity.ok().body(userMapper.userToUserDTO(userService.updateUser(user)));
//        //TODO ask teacher about this because it doesn't work when user wants to update his email
//        Object loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if(loggedUserEmail.equals(updateUserRequest.getEmail())){
//            return ResponseEntity.ok().body(userMapper.userToUserDTO(userService.updateUser(user)));
//        }
//        return ResponseEntity.status(401).build();
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
