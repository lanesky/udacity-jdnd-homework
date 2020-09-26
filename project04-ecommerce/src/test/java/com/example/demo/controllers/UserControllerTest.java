package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private  UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setup() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);

        when(encoder.encode("1234567")).thenReturn("hashed");
        when(userRepository.findById(0L)).thenReturn(Optional.of(new User()));
        when(userRepository.findByUsername("keyun")).thenReturn(new User());
    }

    @Test
    public void create_user_happy_path() {
        CreateUserRequest r = new CreateUserRequest();

        r.setUsername("keyun");
        r.setPassword("1234567");
        r.setConfirmPassword("1234567");

        // create user
        ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("keyun", u.getUsername());
        assertEquals("hashed", u.getPassword());

        // retrieve by id
        response = userController.findById(0L);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        // retrieve by name
        response = userController.findByUserName("keyun");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testPasswordLength() {

        CreateUserRequest r = new CreateUserRequest();

        r.setUsername("keyun");
        r.setPassword("123");
        r.setConfirmPassword("123");

        // create user
        ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());

    }

    @Test
    public void testPasswordConfirm() {
        CreateUserRequest r = new CreateUserRequest();

        r.setUsername("keyun");
        r.setPassword("1234567");
        r.setConfirmPassword("abcdefg");

        // create user
        ResponseEntity<User> response = userController.createUser(r);
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }


}
