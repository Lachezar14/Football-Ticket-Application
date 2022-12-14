package com.ultras.footbalticketsapp.users;

import com.ultras.footbalticketsapp.controller.user.NewPasswordRequest;
import com.ultras.footbalticketsapp.controller.user.UserMapper;
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
    void testSaveUser() {
        //given
        User user = new User(1,"bobby", "smurda", "1234567899", "bobby@gmail.com", "12345", AccountType.USER);

        //when
//        when(userMapper.registerUserRequestToUser(any(RegisterUserRequest.class))).thenReturn(
//                new User(1, "bobby", "smurda", "1234567899", "bobby@gmial.com", "12345", AccountType.USER));

//        when(userMapper.userToUserDTO(any(User.class))).thenReturn(
//                new UserDTO(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", AccountType.USER));

        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("xsdetJ53Y");
        when(userRepository.save(any(User.class))).thenReturn(
                new User(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", "12345", AccountType.USER));

        User created = userService.registerUser(user);

        //then
        //verify(userMapper).registerUserRequestToUser(any(RegisterUserRequest.class));
        //verify(userMapper).userToUserDTO(any(User.class));
        verify(passwordEncoder).encode(any(CharSequence.class));
        verify(userRepository).save(any(User.class));

        assertThat(created.getFirst_name()).isEqualTo(user.getFirst_name());
        assertThat(created.getLast_name()).isEqualTo(user.getLast_name());
        assertThat(created.getEmail()).isEqualTo(user.getEmail());
        assertThat(created.getPhone_number()).isEqualTo(user.getPhone_number());
        assertThat(created.getRole().toString()).isEqualTo(user.getRole().toString());
    }

    @Test
    void testSaveUser_throwsRuntimeException_whenEmailExists(){
        //given
        User user = new User(1,"bobby", "smurda", "1234567899", "bobby@gmail.com", "12345", AccountType.USER);

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
        User userToUpdate = new User(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", "12345",AccountType.USER);
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
        User userToUpdate = new User();

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

        //then
        assertThat(userService.getUserById(1)).isEqualTo(user);
        verify(userRepository).findById((Integer) any());
    }

    @Test
    void testGetUserByEmail() {
        //given
        User user = new User(1, "bobby", "smurda", "1234567899", "bobby@gmail.com", "12345", AccountType.USER);

        //when
        when(userRepository.findByEmail((String) any())).thenReturn(user);
        //when(userMapper.userToUserDTO((User) any())).thenReturn(userDTO);

        //then
        assertThat(user).isEqualTo(userService.getUserByEmail("bobby@gmail.com"));
        verify(userRepository).findByEmail((String) any());
        //verify(userMapper).userToUserDTO((User) any());
    }

    @Test
    void testGetAllUsers() {
        //when
        userService.getAllUsers();
        //then
        verify(userRepository).findAllByRole(AccountType.USER);
    }

    //TODO fix this test
    @Disabled
    @Test
    void testUpdateUser() {
        //given
        User userToUpdate = new User(1, "john", "doe", "1234567899", "johnny@gmail.com", "12345", AccountType.USER);
        User updatedUser = new User(1, "bobby", "smurda", "1235555555", "bobby@gmail.com", "54321", AccountType.USER);

        //when
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(userToUpdate));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        //when(userMapper.userToUserDTO(any(User.class))).thenReturn(updatedUser);
        userService.updateUser(userToUpdate);

        //then
        //verify(userMapper).userToUserDTO(any(User.class));
        verify(userRepository).findById(any(Integer.class));
        verify(userRepository).save(any(User.class));
        assertThat(userToUpdate.getFirst_name()).isEqualTo(updatedUser.getFirst_name());
        assertThat(userToUpdate.getLast_name()).isEqualTo(updatedUser.getLast_name());
        assertThat(userToUpdate.getEmail()).isEqualTo(updatedUser.getEmail());
        assertThat(userToUpdate.getPhone_number()).isEqualTo(updatedUser.getPhone_number());
    }

    @Test
    void testUpdateUser_throwsRuntimeException_whenUserIsNull(){
        //given
        User userToUpdate = new User();

        //when
        when(userRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        //when(userMapper.userToUserDTO(any(User.class))).thenReturn(userDTO);

        //then

        assertThatThrownBy(() -> userService.updateUser(userToUpdate))
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
    void testUpdatePassword_throwsRuntimeException_whenUserIsNull(){
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