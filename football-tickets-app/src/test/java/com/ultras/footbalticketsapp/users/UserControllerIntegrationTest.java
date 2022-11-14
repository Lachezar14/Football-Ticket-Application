package com.ultras.footbalticketsapp.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ultras.footbalticketsapp.controller.UserController;
import com.ultras.footbalticketsapp.controller.user.NewPasswordRequest;
import com.ultras.footbalticketsapp.controller.user.RegisterUserRequest;
import com.ultras.footbalticketsapp.controller.user.UpdateUserRequest;
import com.ultras.footbalticketsapp.controller.user.UserDTO;
import com.ultras.footbalticketsapp.model.AccountType;
import com.ultras.footbalticketsapp.serviceInterface.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
public class UserControllerIntegrationTest {

    @Autowired
    private UserController userController;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    //TODO rename the tests like the picture from bart example
    @Test
    void testRegisterUser() throws Exception {
        when(userService.registerUser((RegisterUserRequest) any()))
                .thenReturn(new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN));

        RegisterUserRequest registerUserRequest =
                new RegisterUserRequest("Jane", "Doe", "4105551212", "jane.doe@example.org", "iloveyou", "ADMIN");
        String content = (objectMapper.writeValueAsString(registerUserRequest));
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

    @Test
    void testRegisterUser2() throws Exception {
        RegisterUserRequest user = new RegisterUserRequest("bobby","smurda", "1234567899", "bobby@gmail.com", "12345", "USER");
        UserDTO userDTO = new UserDTO(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", AccountType.USER);

        when(userService.registerUser(user))
                .thenReturn(userDTO);

        when(userService.getUserByEmail("bobby@gmail.com"))
                .thenReturn(userDTO);

        mockMvc.perform(post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                        .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"first_name\":\"bobby\",\"last_name\":\"smurda\",\"phone_number\":\"1234567899\",\"email\":\"bobby@gmail.com\",\"role\":\"USER\"}"));

        UserDTO addedUser = userService.getUserByEmail("bobby@gmail.com");
        assertThat(addedUser.getFirst_name()).isEqualTo("bobby");
        assertThat(addedUser.getLast_name()).isEqualTo("smurda");
        assertThat(addedUser.getPhone_number()).isEqualTo("1234567899");
        assertThat(addedUser.getEmail()).isEqualTo("bobby@gmail.com");
        assertThat(addedUser.getRole()).isEqualTo(AccountType.USER);
    }

    @Test
    void testMakeUserAdmin() throws Exception {
        UserDTO userDTO = new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.USER);

        doNothing().when(userService).makeUserAdmin((UserDTO) any());

        mockMvc.perform(put("/api/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getUserById(anyInt()))
                .thenReturn(new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN));

        mockMvc.perform(get("/api/{userId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"phone_number\":\"4105551212\",\"email\":\"jane.doe@example"
                                        + ".org\",\"role\":\"ADMIN\"}"));
    }

    @Test
    void testGetUserByEmail() throws Exception {
        when(userService.getUserByEmail((String) any()))
                .thenReturn(new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN));

        mockMvc.perform(get("/api/email/{email}", "jane.doe@example.org"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"phone_number\":\"4105551212\",\"email\":\"jane.doe@example"
                                        + ".org\",\"role\":\"ADMIN\"}"));
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testUpdateUser() throws Exception {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(1, "Jane", "Doe", "4105551212", "jane.doe@example.org");

        when(userService.updateUser((UpdateUserRequest) any()))
                .thenReturn(new UserDTO(1, "Jane", "Doe", "4105551212", "jane.doe@example.org", AccountType.ADMIN));

        mockMvc.perform(put("/api/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserRequest)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                        .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"phone_number\":\"4105551212\",\"email\":\"jane.doe@example"
                                        + ".org\",\"role\":\"ADMIN\"}"));
    }

    @Test
    void testUpdatePassword() throws Exception {
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest(1,"iloveyou", "12345");

        when(userService.updatePassword((NewPasswordRequest) any())).thenReturn(true);

        mockMvc.perform(put("/api/new-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPasswordRequest)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                        .andExpect(MockMvcResultMatchers.content()
                        .string("true"));
    }

    @Test
    void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUserById(anyInt());
        mockMvc.perform(delete("/api/{userId}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
    }
}
