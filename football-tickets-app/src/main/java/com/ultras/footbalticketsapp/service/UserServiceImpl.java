package com.ultras.footbalticketsapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.dto.user.NewPasswordDTO;
import com.ultras.footbalticketsapp.dto.user.NewUserDTO;
import com.ultras.footbalticketsapp.dto.user.UserDTO;
import com.ultras.footbalticketsapp.mapper.UserMapper;
import com.ultras.footbalticketsapp.model.Role;
import com.ultras.footbalticketsapp.model.User;
import com.ultras.footbalticketsapp.repository.RoleRepository;
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
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(User user, String roleName) {
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),authorities);
    }

    @Override
    public void deleteUserById(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        userRepository.delete(user);
    }

    @Override
    public User saveUser(NewUserDTO user) {
        User userEmail = userRepository.findByEmail(user.getEmail());
        if(userEmail != null){
            throw new RuntimeException("Email already in use");
        }
        User newUser = userMapper.newUserDTOtoUser(user);
        addRoleToUser(newUser, user.getRoleName());
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        return userMapper.userToUserDTO(userRepository.findByEmail(email));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        User user = userMapper.userDTOtoUser(userDTO);
        User userToUpdate = userRepository.findById(user.getId()).orElse(null);
        if(userToUpdate != null){
            userToUpdate.setFirst_name(user.getFirst_name());
            userToUpdate.setLast_name(user.getLast_name());
            userToUpdate.setPhone_number(user.getPhone_number());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setRoles(user.getRoles());
        }
        return userRepository.save(userToUpdate);
    }

    @Override
    public boolean updatePassword(NewPasswordDTO newPasswordDTO) {
        User user = userRepository.findById(newPasswordDTO.getId()).orElse(null);
        if(user != null){
            if(bCryptPasswordEncoder.matches(newPasswordDTO.getCurrent_password(), user.getPassword())){
                user.setPassword(bCryptPasswordEncoder.encode(newPasswordDTO.getNew_password()));
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    //@Override
    //public UserDTO getUserDTO(String email) {
    //    return userMapper.userToUserDTO(userRepository.findByEmail(email));
    //}

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
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
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
