package com.ultras.footbalticketsapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.user.NewPasswordRequest;
import com.ultras.footbalticketsapp.controller.user.UserMapper;
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

    public User createAdminIfNotExists(User user){
        if(userRepository.findByEmail(user.getEmail()) == null){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRole(AccountType.ADMIN);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User registerUser(User user) {
        User userEmail = userRepository.findByEmail(user.getEmail());
        if(userEmail != null){
            throw new RuntimeException("Email already in use");
        }
        user.setRole(AccountType.valueOf("USER"));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public void makeUserAdmin(User user) {
        User userToUpdate = userRepository.findById(user.getId()).orElse(null);
        if(userToUpdate == null){
            throw new RuntimeException("User not found");
        }
        userToUpdate.setRole(AccountType.ADMIN);
        userRepository.save(userToUpdate);
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAllByRole(AccountType.USER);
    }

    @Override
    public User updateUser(User updateUser) {
        User userToUpdate = userRepository.findById(updateUser.getId()).orElse(null);
        if(userToUpdate == null){
            throw new RuntimeException("User not found");
        }
        if(userRepository.findByEmail(updateUser.getEmail()) != null & !userToUpdate.getEmail().equals(updateUser.getEmail())){
            throw new RuntimeException("Email already in use");
        }
        userToUpdate.setFirst_name(updateUser.getFirst_name());
        userToUpdate.setLast_name(updateUser.getLast_name());
        userToUpdate.setPhone_number(updateUser.getPhone_number());
        userToUpdate.setEmail(updateUser.getEmail());
        userRepository.save(userToUpdate);
        return userToUpdate;
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
            throw new RuntimeException("Wrong current password");
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
