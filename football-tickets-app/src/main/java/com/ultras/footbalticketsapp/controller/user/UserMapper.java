package com.ultras.footbalticketsapp.controller.user;

import com.ultras.footbalticketsapp.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User registerUserRequestToUser(RegisterUserRequest registerUserRequest);
    User userDTOtoUser(UserResponse UserResponse);
    User updateUserRequestToUser(UpdateUserRequest updateUserRequest);
    UserResponse userToUserDTO(User user);
    List<UserResponse> usersToUsersDTO(List<User> user);
}
