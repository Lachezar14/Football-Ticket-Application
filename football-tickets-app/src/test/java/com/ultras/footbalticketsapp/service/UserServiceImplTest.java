package com.ultras.footbalticketsapp.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ultras.footbalticketsapp.controller.user.UserDTO;
import com.ultras.footbalticketsapp.mapper.UserMapper;
import com.ultras.footbalticketsapp.model.AccountType;
import com.ultras.footbalticketsapp.model.User;
import com.ultras.footbalticketsapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
@Disabled
class UserServiceImplTest {
    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#getUserById(int)}
     */
    @Test
    void testGetUserById() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);
        UserDTO userDTO = new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN);

        when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);
        assertSame(userDTO, userServiceImpl.getUserById(123));
        verify(userRepository).findById((Integer) any());
        verify(userMapper).userToUserDTO((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserById(int)}
     */
    @Test
    void testGetUserById2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);
        when(userMapper.userToUserDTO((User) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> userServiceImpl.getUserById(123));
        verify(userRepository).findById((Integer) any());
        verify(userMapper).userToUserDTO((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserByEmail(String)}
     */
    @Test
    void testGetUserByEmail() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        when(userRepository.findByEmail((String) any())).thenReturn(user);
        UserDTO userDTO = new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN);

        when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);
        assertSame(userDTO, userServiceImpl.getUserByEmail("jane.doe@example.org"));
        verify(userRepository).findByEmail((String) any());
        verify(userMapper).userToUserDTO((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getUserByEmail(String)}
     */
    @Test
    void testGetUserByEmail2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        when(userRepository.findByEmail((String) any())).thenReturn(user);
        when(userMapper.userToUserDTO((User) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> userServiceImpl.getUserByEmail("jane.doe@example.org"));
        verify(userRepository).findByEmail((String) any());
        verify(userMapper).userToUserDTO((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        ArrayList<UserDTO> userDTOList = new ArrayList<>();
        when(userMapper.usersToUsersDTO((List<User>) any())).thenReturn(userDTOList);
        List<UserDTO> actualAllUsers = userServiceImpl.getAllUsers();
        assertSame(userDTOList, actualAllUsers);
        assertTrue(actualAllUsers.isEmpty());
        verify(userRepository).findAll();
        verify(userMapper).usersToUsersDTO((List<User>) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsers2() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        when(userMapper.usersToUsersDTO((List<User>) any())).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> userServiceImpl.getAllUsers());
        verify(userRepository).findAll();
        verify(userMapper).usersToUsersDTO((List<User>) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#getAllUsers()}
     */
    @Test
    void testGetAllUsers3() {
        when(userRepository.findAll()).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> userServiceImpl.getAllUsers());
        verify(userRepository).findAll();
    }


    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserDTO)}
     */
    /*
    @Test
    void testUpdateUser() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        Optional<User> ofResult = Optional.of(user);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirst_name("Jane");
        user1.setId(123);
        user1.setLast_name("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone_number("4105551212");
        user1.setRole(AccountType.ADMIN);
        when(userRepository.save((User) any())).thenReturn(user1);
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirst_name("Jane");
        user2.setId(123);
        user2.setLast_name("Doe");
        user2.setPassword("iloveyou");
        user2.setPhone_number("4105551212");
        user2.setRole(AccountType.ADMIN);
        when(userMapper.userDTOtoUser((UserDTO) any())).thenReturn(user2);
        assertTrue(userServiceImpl
                .updateUser(new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN)));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Integer) any());
        verify(userMapper).userDTOtoUser((UserDTO) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserDTO)}
     */
    /*
    @Test
    void testUpdateUser2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.save((User) any())).thenThrow(new RuntimeException());
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirst_name("Jane");
        user1.setId(123);
        user1.setLast_name("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone_number("4105551212");
        user1.setRole(AccountType.ADMIN);
        when(userMapper.userDTOtoUser((UserDTO) any())).thenReturn(user1);
        assertThrows(RuntimeException.class, () -> userServiceImpl
                .updateUser(new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN)));
        verify(userRepository).save((User) any());
        verify(userRepository).findById((Integer) any());
        verify(userMapper).userDTOtoUser((UserDTO) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserDTO)}
     */
    /*
    @Test
    @Disabled
    void testUpdateUser3() {

        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.orElse(Object)" because the return value of "com.ultras.footbalticketsapp.repository.UserRepository.findById(Object)" is null
        //       at com.ultras.footbalticketsapp.service.UserServiceImpl.updateUser(UserServiceImpl.java:112)
        //   See https://diff.blue/R013 to resolve this issue.

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findById((Integer) any())).thenReturn(null);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirst_name("Jane");
        user1.setId(123);
        user1.setLast_name("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone_number("4105551212");
        user1.setRole(AccountType.ADMIN);
        when(userMapper.userDTOtoUser((UserDTO) any())).thenReturn(user1);
        userServiceImpl
                .updateUser(new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN));
    }

    /**
     * Method under test: {@link UserServiceImpl#updateUser(UserDTO)}
     */
    /*
    @Test
    void testUpdateUser4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        when(userRepository.save((User) any())).thenReturn(user);
        when(userRepository.findById((Integer) any())).thenReturn(Optional.empty());

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirst_name("Jane");
        user1.setId(123);
        user1.setLast_name("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone_number("4105551212");
        user1.setRole(AccountType.ADMIN);
        when(userMapper.userDTOtoUser((UserDTO) any())).thenReturn(user1);
        assertFalse(userServiceImpl
                .updateUser(new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN)));
        verify(userRepository).findById((Integer) any());
        verify(userMapper).userDTOtoUser((UserDTO) any());
    }
    */

    /**
     * Method under test: {@link UserServiceImpl#deleteUserById(int)}
     */
    @Test
    void testDeleteUserById() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        Optional<User> ofResult = Optional.of(user);
        doNothing().when(userRepository).delete((User) any());
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);
        userServiceImpl.deleteUserById(123);
        verify(userRepository).findById((Integer) any());
        verify(userRepository).delete((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUserById(int)}
     */
    @Test
    void testDeleteUserById2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        Optional<User> ofResult = Optional.of(user);
        doThrow(new RuntimeException()).when(userRepository).delete((User) any());
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);
        assertThrows(RuntimeException.class, () -> userServiceImpl.deleteUserById(123));
        verify(userRepository).findById((Integer) any());
        verify(userRepository).delete((User) any());
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUserById(int)}
     */
    @Test
    @Disabled
    void testDeleteUserById3() {
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.orElse(Object)" because the return value of "com.ultras.footbalticketsapp.repository.UserRepository.findById(Object)" is null
        //       at com.ultras.footbalticketsapp.service.UserServiceImpl.deleteUserById(UserServiceImpl.java:139)
        //   See https://diff.blue/R013 to resolve this issue.

        doNothing().when(userRepository).delete((User) any());
        when(userRepository.findById((Integer) any())).thenReturn(null);
        userServiceImpl.deleteUserById(123);
    }

    /**
     * Method under test: {@link UserServiceImpl#deleteUserById(int)}
     */
    @Test
    void testDeleteUserById4() {
        doNothing().when(userRepository).delete((User) any());
        when(userRepository.findById((Integer) any())).thenReturn(Optional.empty());
        userServiceImpl.deleteUserById(123);
        verify(userRepository).findById((Integer) any());
    }
}

