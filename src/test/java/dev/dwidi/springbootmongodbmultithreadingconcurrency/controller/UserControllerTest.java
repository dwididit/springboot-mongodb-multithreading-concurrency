package dev.dwidi.springbootmongodbmultithreadingconcurrency.controller;

import dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.PublicResponseDTO;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.user.UserRequestDTO;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.user.UserResponseDTO;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.entity.User;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.repository.UserRepository;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void createUserSuccessfully() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("newuser");
        userRequestDTO.setEmail("newuser@example.com");
        userRequestDTO.setPhoneNumber("1234567890");

        User user = new User();
        user.setId("1");
        user.setUsername("newuser");
        user.setEmail("newuser@example.com");
        user.setPhoneNumber("1234567890");

        Mockito.when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        CompletableFuture<PublicResponseDTO<UserResponseDTO>> response = userService.createUser(userRequestDTO);
        assertEquals(200, response.join().getCode());
        assertEquals("User created successfully", response.join().getMessage());
    }

    @Test
    void createUserAlreadyExists() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("existinguser");
        userRequestDTO.setEmail("existinguser@example.com");
        userRequestDTO.setPhoneNumber("1234567890");

        User user = new User();
        user.setId("1");
        user.setUsername("existinguser");
        user.setEmail("existinguser@example.com");
        user.setPhoneNumber("1234567890");

        Mockito.when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByEmail("existinguser@example.com")).thenReturn(Optional.of(user));

        CompletableFuture<PublicResponseDTO<UserResponseDTO>> response = userService.createUser(userRequestDTO);
        assertEquals(400, response.join().getCode());
        assertEquals("User already exists", response.join().getMessage());
    }

    @Test
    void getUserByIdSuccessfully() {
        User user = new User();
        user.setId("1");
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPhoneNumber("1234567890");

        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(user));

        CompletableFuture<PublicResponseDTO<UserResponseDTO>> response = userService.getUserById("1");
        assertEquals(200, response.join().getCode());
    }

    @Test
    void getUserByIdNotFound() {
        Mockito.when(userRepository.findById("1")).thenReturn(Optional.empty());

        CompletableFuture<PublicResponseDTO<UserResponseDTO>> response = userService.getUserById("1");
        assertThrows(RuntimeException.class, response::join);
    }

    @Test
    void updateUserByIdSuccessfully() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("updateduser");
        userRequestDTO.setEmail("updateduser@example.com");
        userRequestDTO.setPhoneNumber("0987654321");

        User user = new User();
        user.setId("1");
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPhoneNumber("1234567890");

        User updatedUser = new User();
        updatedUser.setId("1");
        updatedUser.setUsername("updateduser");
        updatedUser.setEmail("updateduser@example.com");
        updatedUser.setPhoneNumber("0987654321");

        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(updatedUser);

        CompletableFuture<PublicResponseDTO<UserResponseDTO>> response = userService.updateUserById("1", userRequestDTO);
        assertEquals(200, response.join().getCode());
        assertEquals("User updated successfully", response.join().getMessage());
    }

    @Test
    void updateUserByIdNotFound() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("updateduser");
        userRequestDTO.setEmail("updateduser@example.com");
        userRequestDTO.setPhoneNumber("0987654321");

        Mockito.when(userRepository.findById("1")).thenReturn(Optional.empty());

        CompletableFuture<PublicResponseDTO<UserResponseDTO>> response = userService.updateUserById("1", userRequestDTO);
        assertThrows(RuntimeException.class, response::join);
    }

    @Test
    void deleteUserByIdSuccessfully() {
        User user = new User();
        user.setId("1");
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPhoneNumber("1234567890");

        Mockito.when(userRepository.findById("1")).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepository).delete(user);

        CompletableFuture<PublicResponseDTO<Object>> response = userService.deleteUserById("1");
        assertEquals(200, response.join().getCode());
        assertEquals("User deleted successfully", response.join().getMessage());
    }

    @Test
    void deleteUserByIdNotFound() {
        Mockito.when(userRepository.findById("1")).thenReturn(Optional.empty());

        CompletableFuture<PublicResponseDTO<Object>> response = userService.deleteUserById("1");
        assertThrows(RuntimeException.class, response::join);
    }
}