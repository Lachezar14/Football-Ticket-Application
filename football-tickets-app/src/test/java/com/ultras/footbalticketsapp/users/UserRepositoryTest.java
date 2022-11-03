package com.ultras.footbalticketsapp.users;

import com.ultras.footbalticketsapp.model.AccountType;
import com.ultras.footbalticketsapp.model.User;
import com.ultras.footbalticketsapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByEmail() {
        //arrange
        String email = "bendover@gmail.com";
        User user = new User(
                1,
                "Ben",
                "Dover",
                "1234567896",
                email,
                "password",
                AccountType.USER
        );
        userRepository.save(user);

        //act
        User foundUser = userRepository.findByEmail(email);

        //assert
        assertEquals(foundUser, user);
    }
}
