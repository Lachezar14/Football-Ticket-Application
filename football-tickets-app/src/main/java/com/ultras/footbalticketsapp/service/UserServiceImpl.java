package com.ultras.footbalticketsapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.user.NewPasswordRequest;
import com.ultras.footbalticketsapp.controller.user.RegisterUserRequest;
import com.ultras.footbalticketsapp.controller.user.UpdateUserRequest;
import com.ultras.footbalticketsapp.controller.user.UserDTO;
import com.ultras.footbalticketsapp.mapper.UserMapper;
import com.ultras.footbalticketsapp.model.AccountType;
import com.ultras.footbalticketsapp.model.User;
import com.ultras.footbalticketsapp.repository.UserRepository;
import com.ultras.footbalticketsapp.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO registerUser(RegisterUserRequest user) {
        User userEmail = userRepository.findByEmail(user.getEmail());
        if(userEmail != null){
            throw new RuntimeException("Email already in use");
        }
        User newUser = userMapper.registerUserRequestToUser(user);
        newUser.setRole(AccountType.valueOf(user.getRoleName()));
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
        return userMapper.userToUserDTO(newUser);
    }

    @Override
    public void makeUserAdmin(UserDTO user) {
        User userToUpdate = userRepository.findById(user.getId()).orElse(null);
        if(userToUpdate == null){
            throw new RuntimeException("User not found");
        }
        userToUpdate.setRole(AccountType.ADMIN);
        userRepository.save(userToUpdate);
    }

    @Override
    public UserDTO getUserById(int userId) {
        return userMapper.userToUserDTO(userRepository.findById(userId).orElse(null));
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userMapper.userToUserDTO(userRepository.findByEmail(email));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found in the database");
        }

        /*Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });*/
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userMapper.usersToUsersDTO(userRepository.findAllByRole(AccountType.USER));
    }

    @Override
    public UserDTO updateUser(UpdateUserRequest updateUserRequest) {
        User userToUpdate = userRepository.findById(updateUserRequest.getId()).orElse(null);
        if(userToUpdate == null){
            throw new RuntimeException("User not found");
        }
        if(userRepository.findByEmail(updateUserRequest.getEmail()) != null){
            throw new RuntimeException("Email already in use");
        }
        userToUpdate.setFirst_name(updateUserRequest.getFirst_name());
        userToUpdate.setLast_name(updateUserRequest.getLast_name());
        userToUpdate.setPhone_number(updateUserRequest.getPhone_number());
        userToUpdate.setEmail(updateUserRequest.getEmail());
        userRepository.save(userToUpdate);
        return userMapper.userToUserDTO(userToUpdate);
    }

    @Override
    public boolean updatePassword(NewPasswordRequest newPasswordRequest) {
        User user = userRepository.findById(newPasswordRequest.getId()).orElse(null);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        if(bCryptPasswordEncoder.matches(newPasswordRequest.getCurrent_password(), user.getPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(newPasswordRequest.getNew_password()));
            userRepository.save(user);
            return true;
        }
        else
        {
            throw new RuntimeException("Wrong password");
        }
    }

    @Override
    public void deleteUserById(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null){
            throw new RuntimeException("User not found");
        }
        userRepository.delete(user);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userRepository.findByEmail(username);
                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new java.sql.Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        //.withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .withClaim("role", user.getRole().toString())
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
            catch (Exception ex) {
                response.setHeader("error", ex.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", ex.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else
        {
            throw new RuntimeException("Refresh token is missing");
        }

    }

}
