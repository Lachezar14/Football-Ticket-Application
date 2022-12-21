package com.ultras.footbalticketsapp.users;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.UserController;
import com.ultras.footbalticketsapp.controller.user.*;
import com.ultras.footbalticketsapp.controller.user.UserResponse;
import com.ultras.footbalticketsapp.model.AccountType;
import com.ultras.footbalticketsapp.model.User;
import com.ultras.footbalticketsapp.serviceInterface.UserService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@WebMvcTest
@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserService userService;

    @Mock
    private Authentication auth;

    @BeforeEach
    public void initSecurityContext() {
        when(auth.getPrincipal()).thenReturn("leclerc@gmail.com");
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    public void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }


    @Test
    void testRegisterUser() throws Exception {
        //given
        User user = new User(1, "Jane", "Doe", "4105551212","jane.doe@example.org","iloveyou", AccountType.USER);
        RegisterUserRequest registerUserRequest = new RegisterUserRequest("Jane", "Doe", "4105551212","jane.doe@example.org","iloveyou", "USER");

        //when
        when(userService.registerUser((User) any())).thenReturn(user);
        when(userMapper.userToUserDTO((User) any()))
                .thenReturn(new UserResponse(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.USER));
        when(userMapper.registerUserRequestToUser((RegisterUserRequest) any())).thenReturn(user);

        //then
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
                                        + ".org\",\"role\":\"USER\"}"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/api/1"));
    }

    @Test
    void testMakeUserAdmin() throws Exception {
        //given
        User user = new User(1, "Jane", "Doe", "4105551212","jane.doe@example.org","iloveyou", AccountType.USER);
        UserResponse userResponse = new UserResponse(1,"Jane", "Doe", "4105551212","jane.doe@example.org", AccountType.ADMIN);

        //when
        doNothing().when(userService).makeUserAdmin((User) any());
        when(userMapper.userDTOtoUser((UserResponse) any())).thenReturn(user);

        //then
        String content = (new ObjectMapper()).writeValueAsString(userResponse);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetUserById() throws Exception {
        //given
        User user = new User(1, "Jane", "Doe", "4105551212","jane.doe@example.org","iloveyou", AccountType.USER);

        //when
        when(userService.getUserById(anyInt())).thenReturn(user);
        when(userMapper.userToUserDTO((User) any()))
                .thenReturn(new UserResponse(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.USER));

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/{userId}", 1);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"phone_number\":\"4105551212\",\"email\":\"jane.doe@example"
                                        + ".org\",\"role\":\"USER\"}"));
    }

    @Test
    @WithMockUser(username = "leclerc@gmail.com", password = "12345", roles = "USER")
    void testGetUserByEmail() throws Exception {
        //given
        User user = new User(1,"Charles","Leclerc","2840164926","leclerc@gmail.com","12345",AccountType.USER);
        UserResponse userResponse = new UserResponse(1,"Charles","Leclerc","2840164926","leclerc@gmail.com",AccountType.USER);

        //when
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(userMapper.userToUserDTO((User) any())).thenReturn(userResponse);

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/email/{email}", "leclerc@gmail.com");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"first_name\":\"Charles\",\"last_name\":\"Leclerc\",\"phone_number\":\"2840164926\",\"email\":\"leclerc@gmail.com\",\"role\":\"USER\"}"));
    }

    @Test
    void testGetUserByEmail_throws401Unauthorized_whenAnotherUserTriesToAccessAnotherPersonInfo() throws Exception {
        //given
        User user = new User(1, "Lele", "Mele", "2840164926", "gg@gmail.com", "12345", AccountType.USER);
        UserResponse userResponse = new UserResponse(1, "Lele", "Mele", "2840164926", "gg@gmail.com", AccountType.USER);

        //when
        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(userMapper.userToUserDTO((User) any())).thenReturn(userResponse);

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/email/{email}", "gg@gmail.com");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void testGetAllUsers() throws Exception {
        //when
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        when(userMapper.usersToUsersDTO((List<User>) any())).thenReturn(new ArrayList<>());

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testUpdateUser() throws Exception {
        //given
        User user = new User(1, "Jane", "Doe", "4105551212","jane.doe@example.org","iloveyou", AccountType.USER);
        UpdateUserRequest request = new UpdateUserRequest(1,"Jane", "Doe", "4105551212","jane.doe@example.org");

        //when
        when(userService.updateUser((User) any())).thenReturn(user);
        when(userMapper.userToUserDTO((User) any()))
                .thenReturn(new UserResponse(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.USER));
        when(userMapper.updateUserRequestToUser((UpdateUserRequest) any())).thenReturn(user);

        //then
        String content = (new ObjectMapper()).writeValueAsString(request);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/{userId}", 1)
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
                                        + ".org\",\"role\":\"USER\"}"));
    }

    @Test
    void testUpdatePassword() throws Exception {
        //given
        NewPasswordRequest request = new NewPasswordRequest(1,"12345","iloveyou");

        //when
        when(userService.updatePassword((NewPasswordRequest) any())).thenReturn(true);

        //then
        String content = (new ObjectMapper()).writeValueAsString(request);
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

    @Test
    void testDeleteUser() throws Exception {
        //when
        doNothing().when(userService).deleteUserById(anyInt());

        //then
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/{userId}", 1);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
    }
}
