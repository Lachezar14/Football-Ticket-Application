package com.ultras.footbalticketsapp.mapper;

import com.ultras.footbalticketsapp.controller.user.RegisterUserRequest;
import com.ultras.footbalticketsapp.controller.user.UpdateUserRequest;
import com.ultras.footbalticketsapp.controller.user.UserDTO;
import com.ultras.footbalticketsapp.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User registerUserRequestToUser(RegisterUserRequest registerUserRequest);
    User userDTOtoUser(UserDTO UserDTO);
    User updateUserRequestToUser(UpdateUserRequest updateUserRequest);
    UserDTO userToUserDTO(User user);
    List<UserDTO> usersToUsersDTO(List<User> user);
}
