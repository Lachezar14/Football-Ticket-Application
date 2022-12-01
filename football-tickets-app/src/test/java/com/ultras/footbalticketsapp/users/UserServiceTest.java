package com.ultras.footbalticketsapp.users;

import com.ultras.footbalticketsapp.controller.user.NewPasswordRequest;
import com.ultras.footbalticketsapp.controller.user.RegisterUserRequest;
import com.ultras.footbalticketsapp.controller.user.UpdateUserRequest;
import com.ultras.footbalticketsapp.controller.user.UserDTO;
import com.ultras.footbalticketsapp.mapper.UserMapper;
import com.ultras.footbalticketsapp.model.AccountType;
import com.ultras.footbalticketsapp.model.User;
import com.ultras.footbalticketsapp.repository.UserRepository;
import com.ultras.footbalticketsapp.service.UserServiceImpl;
import com.ultras.footbalticketsapp.serviceInterface.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class  UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(
                userRepository,passwordEncoder,userMapper);
    }

    @Test
    @Disabled
    void saveRole() {
    }

    @Test
    @Disabled
    void addRoleToUser() {
    }

    @Test
    @Disabled
    void loadUserByUsername() {
    }

    @Test
    void testSaveUser() {
        //given
        RegisterUserRequest user = new RegisterUserRequest("bobby", "smurda", "1234567899", "bobby@gmail.com", "12345", "USER");

        //when
        when(userMapper.registerUserRequestToUser(any(RegisterUserRequest.class))).thenReturn(
                new User(1, "bobby", "smurda", "1234567899", "bobby@gmial.com", "12345", AccountType.USER));

        when(userMapper.userToUserDTO(any(User.class))).thenReturn(
                new UserDTO(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", AccountType.USER));

        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("xsdetJ53Y");
        when(userRepository.save(any(User.class))).thenReturn(
                new User(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", "12345", AccountType.USER));

        UserDTO created = userService.registerUser(user);

        //then
        verify(userMapper).registerUserRequestToUser(any(RegisterUserRequest.class));
        verify(userMapper).userToUserDTO(any(User.class));
        verify(passwordEncoder).encode(any(CharSequence.class));
        verify(userRepository).save(any(User.class));

        assertThat(created.getFirst_name()).isEqualTo(user.getFirst_name());
        assertThat(created.getLast_name()).isEqualTo(user.getLast_name());
        assertThat(created.getEmail()).isEqualTo(user.getEmail());
        assertThat(created.getPhone_number()).isEqualTo(user.getPhone_number());
        assertThat(created.getRole().toString()).isEqualTo(user.getRoleName());
    }

    @Test
    void testSaveUser_throwsRuntimeException_whenEmailExists(){
        //given
        RegisterUserRequest user = new RegisterUserRequest("bobby", "smurda", "1234567899", "bobby@gmail.com", "12345", "ROLE_USER");

        //when
        given(userRepository.findByEmail(user.getEmail())).willReturn(
                new User(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", "12345", AccountType.USER));

        //then
        assertThatThrownBy(() -> userService.registerUser(user))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Email already in use");

        verify(userRepository, never()).save(any());
    }

    @Test
    void testMakeUserAdmin(){
        //given
        UserDTO userToUpdate = new UserDTO(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", AccountType.USER);
        User user = new User(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", "12345", AccountType.USER);

        //when
        when(userRepository.findById(userToUpdate.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.makeUserAdmin(userToUpdate);

        //then
        verify(userRepository).findById(userToUpdate.getId());
        verify(userRepository).save(any(User.class));
        assertThat(user.getRole()).isEqualTo(AccountType.ADMIN);
    }

    @Test
    void testMakeUser_throwsRuntimeException_whenAdminUserIsNull(){
        //given
        UserDTO userToUpdate = new UserDTO();

        //when
        when(userRepository.findById(userToUpdate.getId())).thenReturn(Optional.empty());

        //then

        assertThatThrownBy(() -> userService.makeUserAdmin(userToUpdate))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");

        verify(userRepository).findById(userToUpdate.getId());


    }

    @Test
    void testGetUserById() {
        //given
        User user = new User(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", "12345", AccountType.USER);
        Optional<User> ofResult = Optional.of(user);

        //when
        when(userRepository.findById((Integer) any())).thenReturn(ofResult);
        UserDTO userDTO = new UserDTO(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", AccountType.USER);
        when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);

        //then
        assertThat(userService.getUserById(1)).isEqualTo(userDTO);
        verify(userRepository).findById((Integer) any());
        verify(userMapper).userToUserDTO((User) any());
    }

    @Test
    void testGetUserByEmail() {
        //given
        User user = new User(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", "12345", AccountType.USER);
        UserDTO userDTO = new UserDTO(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", AccountType.USER);

        //when
        when(userRepository.findByEmail((String) any())).thenReturn(user);
        when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);

        //then
        assertThat(userDTO).isEqualTo(userService.getUserByEmail("bobby@gmail.com"));
        verify(userRepository).findByEmail((String) any());
        verify(userMapper).userToUserDTO((User) any());
    }

    @Test
    void testGetAllUsers() {
        //when
        userService.getAllUsers();
        //then
        verify(userRepository).findAllByRole(AccountType.USER);
    }


    @Test
    void testUpdateUser() {
        //given
        UpdateUserRequest user = new UpdateUserRequest(1,"bobby", "smurda", "1234567899", "bobby@gmail.com");
        UserDTO updatedUser = new UserDTO(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", AccountType.USER);
        User userToUpdate = new User(1, "john", "doe", "1234567899", "johnny@gmail.com", "12345", AccountType.USER);

        //when
        when(userRepository.save(any(User.class))).thenReturn(userToUpdate);
        when(userMapper.userToUserDTO(any(User.class))).thenReturn(updatedUser);
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(userToUpdate));
        userService.updateUser(user);

        //then
        verify(userRepository).save(any(User.class));
        verify(userMapper).userToUserDTO(any(User.class));
        verify(userRepository).findById(any(Integer.class));
        assertThat(userToUpdate.getFirst_name()).isEqualTo(updatedUser.getFirst_name());
        assertThat(userToUpdate.getLast_name()).isEqualTo(updatedUser.getLast_name());
        assertThat(userToUpdate.getEmail()).isEqualTo(updatedUser.getEmail());
        assertThat(userToUpdate.getPhone_number()).isEqualTo(updatedUser.getPhone_number());
    }

    @Test
    void testUpdateUser_throwsRuntimeException_whenUserIsNull(){
        //given
        UserDTO userDTO = new UserDTO();
        UpdateUserRequest user = new UpdateUserRequest();
        User userToUpdate = new User();

        //when
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        //when(userMapper.userToUserDTO(any(User.class))).thenReturn(userDTO);

        //then

        assertThatThrownBy(() -> userService.updateUser(user))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");

        verify(userRepository).findById(any(Integer.class));
        //verify(userMapper).userToUserDTO(any(User.class));

    }

    @Test
    void testUpdatePassword() {
        //given
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest(1, "12345", "67890");
        User user = new User(1, "bobby", "smurda", "1234567899", "bobby@gmial.com", "12345", AccountType.USER);

        //when
        when(userRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        userService.updatePassword(newPasswordRequest);


        //then
        verify(userRepository).findById(any(Integer.class));
        verify(passwordEncoder).matches(any(), any());

        //assert that it returns true
        assertTrue(passwordEncoder.matches(newPasswordRequest.getNew_password(), user.getPassword()));
    }

    @Test
    void testUpdatePassword_throwsRuntimeException_whenCurrentPasswordIsWrong(){
        //given
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest(1, "12345", "67890");
        User user = new User();

        //when
        when(userRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.of(user));
        //userService.updatePassword(newPasswordRequest);

        //then

        assertThatThrownBy(() -> userService.updatePassword(newPasswordRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Wrong password");

        verify(userRepository).findById(any(Integer.class));
    }

    @Test
    void testupdatePassword_throwsRuntimeException_whenUserIsNull(){
        //given
        NewPasswordRequest newPasswordRequest = new NewPasswordRequest(1, "12345", "67890");
        User user = new User();

        //when
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        //userService.updatePassword(newPasswordRequest);

        //then

        assertThatThrownBy(() -> userService.updatePassword(newPasswordRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");

        verify(userRepository).findById(any(Integer.class));
    }

    @Test
    void testDeleteUserById() {
        //given
        User user = new User(1,"bobby","smurda","1234567899","bobby@gmail.com","12345", AccountType.USER);

        //when
        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user))
                .thenReturn(Optional.empty());

        userService.deleteUserById(user.getId());

        //then
        verify(userRepository).findById((Integer) any());
        assertThat(userRepository.findById(1)).isEmpty();
    }

    @Test
    void testDeleteUserById_throwsRuntimeException_whenUserIsNull() {
        //given
        User user = new User();

        //when
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> userService.deleteUserById(user.getId()))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("User not found");

        verify(userRepository).findById((Integer) any());
    }
}