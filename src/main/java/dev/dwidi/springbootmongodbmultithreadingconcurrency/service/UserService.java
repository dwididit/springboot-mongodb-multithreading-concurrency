package dev.dwidi.springbootmongodbmultithreadingconcurrency.service;

import dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.PublicResponseDTO;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.user.UserRequestDTO;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.user.UserResponseDTO;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.entity.User;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.exceptions.UserAlreadyExistsException;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.exceptions.UserNotFoundException;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.notification.EmailService;
import dev.dwidi.springbootmongodbmultithreadingconcurrency.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The UserService class is a service for the User entity.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    private final EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    private final ConcurrentHashMap<String, User> userCache = new ConcurrentHashMap<>();

    /**
     * Creates a user.
     *
     * @param userRequestDTO the user request DTO
     * @return the public response DTO
     */
    public CompletableFuture<PublicResponseDTO<UserResponseDTO>> createUser(UserRequestDTO userRequestDTO) {
        logger.info("Executing createUser with userRequestDTO: {}", userRequestDTO);
        return CompletableFuture.supplyAsync(() -> {
            logger.info("Executing supplyAsync in createUser on thread: {}", Thread.currentThread().getName());
            try {
                if (userRepository.findByUsername(userRequestDTO.getUsername()).isPresent() ||
                        userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()) {
                    throw new UserAlreadyExistsException("User already exists");
                }

                // Create and save the user
                User user = new User();
                user.setUsername(userRequestDTO.getUsername());
                user.setEmail(userRequestDTO.getEmail());
                user.setPhoneNumber(userRequestDTO.getPhoneNumber());
                User savedUser = userRepository.save(user);

                // Cache the user
                userCache.put(savedUser.getId(), savedUser);

                // Create the response DTO
                UserResponseDTO userResponseDTO = new UserResponseDTO(savedUser);

                // Send the email after the user is successfully created
                CompletableFuture.runAsync(() -> {
                    try {
                        emailService.sendEmail(
                                savedUser.getEmail(),
                                "no-reply@email.dwidi.dev",
                                "Welcome to Java Spring Boot App!",
                                "Dear " + savedUser.getUsername() + ",\n\nThank you for registering with us."
                        ).join(); // Wait for the email to be sent
                        logger.info("Email sent to: {}", savedUser.getEmail());
                    } catch (Exception e) {
                        logger.error("Failed to send email to: {}", savedUser.getEmail(), e);
                    }
                });

                // Return the successful response
                return new PublicResponseDTO<>(200, "User created successfully", userResponseDTO);
            } catch (RuntimeException e) {
                logger.error("Error creating user: ", e);
                return new PublicResponseDTO<>(400, "User already exists", null);
            }
        });
    }

    /**
     * Gets all users.
     *
     * @return the public response DTO
     */
    public CompletableFuture<PublicResponseDTO<UserResponseDTO>> getUserById(String userId) {
        logger.info("Executing getUserById with userId: {} on thread: {}", userId, Thread.currentThread().getName());
        User user = userCache.get(userId);
        if (user != null) {
            return CompletableFuture.completedFuture(
                    new PublicResponseDTO<>(200, "User retrieved from cache", new UserResponseDTO(user))
            );
        }
        return CompletableFuture.supplyAsync(() -> userRepository.findById(userId)
                .map(user1 -> {
                    userCache.put(user1.getId(), user1);
                    return new PublicResponseDTO<>(200, "User retrieved from repository", new UserResponseDTO(user1));
                })
                .orElseThrow(() -> new UserNotFoundException("User not found")));
    }
    /**
     * Gets all users.
     *
     * @return the public response DTO
     */
    public CompletableFuture<PublicResponseDTO<UserResponseDTO>> updateUserById(String userId, UserRequestDTO userRequestDTO) {
        logger.info("Executing updateUserById with userId: {} and userRequestDTO: {} on thread: {}", userId, userRequestDTO, Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> userRepository.findById(userId)
                .map(user -> {
                    user.setUsername(userRequestDTO.getUsername());
                    user.setEmail(userRequestDTO.getEmail());
                    user.setPhoneNumber(userRequestDTO.getPhoneNumber());
                    User updatedUser = userRepository.save(user);
                    userCache.put(updatedUser.getId(), updatedUser);
                    return new PublicResponseDTO<>(200, "User updated successfully", new UserResponseDTO(updatedUser));
                })
                .orElseThrow(() -> new UserNotFoundException("User not found")));
    }
    /**
     * Deletes a user.
     *
     * @param userId the user ID
     * @return the public response DTO
     */
    public CompletableFuture<PublicResponseDTO<Object>> deleteUserById(String userId) {
        logger.info("Executing supplyAsync in updateUserById on thread: {}", Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> userRepository.findById(userId)
                .map(user -> {
                    userRepository.delete(user);
                    userCache.remove(userId);
                    return new PublicResponseDTO<>(200, "User deleted successfully", null);
                })
                .orElseThrow(() -> new UserNotFoundException("User not found")));
    }
}