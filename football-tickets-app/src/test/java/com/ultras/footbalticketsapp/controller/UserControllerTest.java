package com.ultras.footbalticketsapp.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.user.NewPasswordRequest;
import com.ultras.footbalticketsapp.controller.user.RegisterUserRequest;
import com.ultras.footbalticketsapp.controller.user.UpdateUserRequest;
import com.ultras.footbalticketsapp.controller.user.UserDTO;
import com.ultras.footbalticketsapp.model.AccountType;
import com.ultras.footbalticketsapp.serviceInterface.UserService;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
@Disabled
class UserControllerTest {
    @Autowired
    private UserController userController;

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
     * Method under test: {@link UserController#registerUser(RegisterUserRequest)}
     */
    @Test
    void testRegisterUser() throws Exception {
        when(userService.registerUser((RegisterUserRequest) any()))
                .thenReturn(new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN));

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
     * Method under test: {@link UserController#makeUserAdmin(UserDTO)}
     */
    @Test
    void testMakeUserAdmin() throws Exception {
        doNothing().when(userService).makeUserAdmin((UserDTO) any());

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setFirst_name("Jane");
        userDTO.setId(1);
        userDTO.setLast_name("Doe");
        userDTO.setPhone_number("4105551212");
        userDTO.setRole(AccountType.ADMIN);
        String content = (new ObjectMapper()).writeValueAsString(userDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/admin")
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
        when(userService.getUserById(anyInt()))
                .thenReturn(new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN));
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
     * Method under test: {@link UserController#getUserByEmail(String)}
     */
    @Test
    void testGetUserByEmail() throws Exception {
        when(userService.getUserByEmail((String) any()))
                .thenReturn(new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/email/{email}",
                "jane.doe@example.org");
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    void testGetAllUsers2() throws Exception {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/api/users");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link UserController#updateUser(UpdateUserRequest)}
     */
    @Test
    void testUpdateUser() throws Exception {
        when(userService.updateUser((UpdateUserRequest) any()))
                .thenReturn(new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN));

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail("jane.doe@example.org");
        updateUserRequest.setFirst_name("Jane");
        updateUserRequest.setId(1);
        updateUserRequest.setLast_name("Doe");
        updateUserRequest.setPhone_number("4105551212");
        String content = (new ObjectMapper()).writeValueAsString(updateUserRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/update")
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
}

