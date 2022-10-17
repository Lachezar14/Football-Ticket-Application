package com.ultras.footbalticketsapp.mapper;

import com.ultras.footbalticketsapp.dto.user.NewUserDTO;
import com.ultras.footbalticketsapp.dto.user.UserDTO;
import com.ultras.footbalticketsapp.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User newUserDTOtoUser(NewUserDTO newUserDTO);
    User userDTOtoUser(UserDTO UserDTO);
    UserDTO userToUserDTO(User user);
    List<UserDTO> usersToUsersDTO(List<User> user);
}
