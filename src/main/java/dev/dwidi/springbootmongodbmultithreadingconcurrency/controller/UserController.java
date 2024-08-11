package dev.dwidi.springbootmongodbmultithreadingconcurrency.controller;

import dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.PublicResponseDTO;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.user.UserRequestDTO;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.user.UserResponseDTO;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

/**
 * The UserController class represents a REST controller for user entities.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     *
     * @param userRequestDTO the user request DTO
     * @return a CompletableFuture containing the public response DTO
     */
    @PostMapping("/create")
    public CompletableFuture<PublicResponseDTO<UserResponseDTO>> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param userId the ID of the user to be retrieved
     * @return a CompletableFuture containing the public response DTO
     */
    @GetMapping("/get")
    public CompletableFuture<PublicResponseDTO<UserResponseDTO>> getUserById(@RequestParam String userId) {
        return userService.getUserById(userId);
    }

    /**
     * Updates a user by its ID.
     *
     * @param userId the ID of the user to be updated
     * @param userRequestDTO the user request DTO
     * @return a CompletableFuture containing the public response DTO
     */
    @PutMapping("/update")
    public CompletableFuture<PublicResponseDTO<UserResponseDTO>> updateUserById(@RequestParam String userId, @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUserById(userId, userRequestDTO);
    }

    /**
     * Deletes a user by its ID.
     *
     * @param userId the ID of the user to be deleted
     * @return a CompletableFuture containing the public response DTO
     */
    @DeleteMapping("/delete")
    public CompletableFuture<PublicResponseDTO<Object>> deleteUserById(@RequestParam String userId) {
        return userService.deleteUserById(userId);
    }
}
