package com.ultras.footbalticketsapp.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.user.*;
import com.ultras.footbalticketsapp.controller.user.UserResponse;
import com.ultras.footbalticketsapp.model.AccountType;
import com.ultras.footbalticketsapp.model.User;
import com.ultras.footbalticketsapp.serviceInterface.UserService;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Disabled
@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#deleteUser(int)}
     */
    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUserById(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/{userId}", 123);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
    }

    /**
     * Method under test: {@link UserController#deleteUser(int)}
     */
    @Test
    void testDeleteUser2() throws Exception {
        doNothing().when(userService).deleteUserById(anyInt());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/{userId}", 123);
        deleteResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
    }

    /**
     * Method under test: {@link UserController#getUserByEmail(String)}
     */
    @Test
    void testGetUserByEmail() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UserController#registerUser(RegisterUserRequest)}
     */
    @Test
    void testRegisterUser() throws Exception {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        when(userService.registerUser((User) any())).thenReturn(user);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirst_name("Jane");
        user1.setId(123);
        user1.setLast_name("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone_number("4105551212");
        user1.setRole(AccountType.ADMIN);
        when(userMapper.userToUserDTO((User) any()))
                .thenReturn(new UserResponse(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN));
        when(userMapper.registerUserRequestToUser((RegisterUserRequest) any())).thenReturn(user1);

        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("jane.doe@example.org");
        registerUserRequest.setFirst_name("Jane");
        registerUserRequest.setLast_name("Doe");
        registerUserRequest.setPassword("iloveyou");
        registerUserRequest.setPhone_number("4105551212");
        registerUserRequest.setRoleName("Role Name");
        String content = (new ObjectMapper()).writeValueAsString(registerUserRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"phone_number\":\"4105551212\",\"email\":\"jane.doe@example"
                                        + ".org\",\"role\":\"ADMIN\"}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/api/1"));
    }

    /**
     * Method under test: {@link UserController#makeUserAdmin(UserResponse)}
     */
    @Test
    void testMakeUserAdmin() throws Exception {
        doNothing().when(userService).makeUserAdmin((User) any());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        when(userMapper.userDTOtoUser((UserResponse) any())).thenReturn(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail("jane.doe@example.org");
        userResponse.setFirst_name("Jane");
        userResponse.setId(1);
        userResponse.setLast_name("Doe");
        userResponse.setPhone_number("4105551212");
        userResponse.setRole(AccountType.ADMIN);
        String content = (new ObjectMapper()).writeValueAsString(userResponse);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#getUserById(int)}
     */
    @Test
    void testGetUserById() throws Exception {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        when(userService.getUserById(anyInt())).thenReturn(user);
        when(userMapper.userToUserDTO((User) any()))
                .thenReturn(new UserResponse(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/{userId}", 123);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"phone_number\":\"4105551212\",\"email\":\"jane.doe@example"
                                        + ".org\",\"role\":\"ADMIN\"}"));
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        when(userMapper.usersToUsersDTO((List<User>) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link UserController#updateUser(UpdateUserRequest, int)}
     */
    @Test
    void testUpdateUser() throws Exception {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(123);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("4105551212");
        user.setRole(AccountType.ADMIN);
        when(userService.updateUser((User) any())).thenReturn(user);

        User user1 = new User();
        user1.setEmail("jane.doe@example.org");
        user1.setFirst_name("Jane");
        user1.setId(123);
        user1.setLast_name("Doe");
        user1.setPassword("iloveyou");
        user1.setPhone_number("4105551212");
        user1.setRole(AccountType.ADMIN);
        when(userMapper.userToUserDTO((User) any()))
                .thenReturn(new UserResponse(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN));
        when(userMapper.updateUserRequestToUser((UpdateUserRequest) any())).thenReturn(user1);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail("jane.doe@example.org");
        updateUserRequest.setFirst_name("Jane");
        updateUserRequest.setId(1);
        updateUserRequest.setLast_name("Doe");
        updateUserRequest.setPhone_number("4105551212");
        String content = (new ObjectMapper()).writeValueAsString(updateUserRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/{userId}", 123)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"phone_number\":\"4105551212\",\"email\":\"jane.doe@example"
                                        + ".org\",\"role\":\"ADMIN\"}"));
    }

    /**
     * Method under test: {@link UserController#updatePassword(NewPasswordRequest)}
     */
    @Test
    void testUpdatePassword() throws Exception {
        when(userService.updatePassword((NewPasswordRequest) any())).thenReturn(true);

        NewPasswordRequest newPasswordRequest = new NewPasswordRequest();
        newPasswordRequest.setCurrent_password("iloveyou");
        newPasswordRequest.setId(1);
        newPasswordRequest.setNew_password("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(newPasswordRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/new-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Method under test: {@link UserController#refreshToken(HttpServletRequest, HttpServletResponse)}
     */
    @Test
    void testRefreshToken() throws Exception {
        doNothing().when(userService).refreshToken((HttpServletRequest) any(), (HttpServletResponse) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/token/refresh");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

