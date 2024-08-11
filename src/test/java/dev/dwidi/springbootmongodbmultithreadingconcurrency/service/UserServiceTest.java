

package dev.dwidi.springbootmongodbmultithreadingconcurrency.service;

import dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.PublicResponseDTO;import dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.user.UserRequestDTO;import dev.dwidi.springbootmongodbmultithreadingconcurrency.dto.user.UserResponseDTO;import dev.dwidi.springbootmongodbmultithreadingconcurrency.entity.User;import dev.dwidi.springbootmongodbmultithreadingconcurrency.repository.UserRepository;import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import java.util.Optional;import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;import java.util.concurrent.Future;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
    import static org.mockito.ArgumentMatchers.any;
    import static org.mockito.ArgumentMatchers.anyInt;
    import static org.mockito.ArgumentMatchers.anyString;
    import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

 @Mock         private UserRepository mockUserRepository;
 @Mock         private ConcurrentHashMap<String,User> mockUserCache;

    @Test
    void testCreateUser() throws Exception {
    // Setup
                     final UserService userServiceUnderTest = new UserService(mockUserRepository) ;
        // TODO: Set the following fields: userCache.
                final UserRequestDTO userRequestDTO = new UserRequestDTO("username", "email", "phoneNumber");
                
            // Configure UserRepository.findByUsername(...).
                                                        final User user1 = new User();
                user1.setId("id");
                user1.setUsername("username");
                user1.setEmail("email");
                user1.setPhoneNumber("phoneNumber");
        final Optional<User> user = Optional.of(user1);
            when( mockUserRepository .findByUsername("username")).thenReturn(user);

            // Configure UserRepository.findByEmail(...).
                                                        final User user3 = new User();
                user3.setId("id");
                user3.setUsername("username");
                user3.setEmail("email");
                user3.setPhoneNumber("phoneNumber");
        final Optional<User> user2 = Optional.of(user3);
            when( mockUserRepository .findByEmail("email")).thenReturn(user2);

            // Configure UserRepository.save(...).
        final User user4 = new User();
                user4.setId("id");
                user4.setUsername("username");
                user4.setEmail("email");
                user4.setPhoneNumber("phoneNumber");
        final User entity = new User();
                entity.setId("id");
                entity.setUsername("username");
                entity.setEmail("email");
                entity.setPhoneNumber("phoneNumber");
            when( mockUserRepository .save(entity)).thenReturn(user4);

    // Run the test
 final CompletableFuture<PublicResponseDTO<UserResponseDTO>> result =  userServiceUnderTest.createUser(userRequestDTO);

        // Verify the results
        // Confirm ConcurrentHashMap.put(...).
        final User value = new User();
                value.setId("id");
                value.setUsername("username");
                value.setEmail("email");
                value.setPhoneNumber("phoneNumber");
            verify( mockUserCache ).put("id",value);
    }
                                                                                                
    @Test
    void testCreateUser_UserRepositoryFindByUsernameReturnsAbsent() throws Exception {
    // Setup
                     final UserService userServiceUnderTest = new UserService(mockUserRepository) ;
        // TODO: Set the following fields: userCache.
                final UserRequestDTO userRequestDTO = new UserRequestDTO("username", "email", "phoneNumber");
        when( mockUserRepository .findByUsername("username")).thenReturn( Optional.empty() );
                
            // Configure UserRepository.save(...).
        final User user = new User();
                user.setId("id");
                user.setUsername("username");
                user.setEmail("email");
                user.setPhoneNumber("phoneNumber");
        final User entity = new User();
                entity.setId("id");
                entity.setUsername("username");
                entity.setEmail("email");
                entity.setPhoneNumber("phoneNumber");
            when( mockUserRepository .save(entity)).thenReturn(user);

    // Run the test
 final CompletableFuture<PublicResponseDTO<UserResponseDTO>> result =  userServiceUnderTest.createUser(userRequestDTO);

        // Verify the results
        // Confirm ConcurrentHashMap.put(...).
        final User value = new User();
                value.setId("id");
                value.setUsername("username");
                value.setEmail("email");
                value.setPhoneNumber("phoneNumber");
            verify( mockUserCache ).put("id",value);
    }
                                                                        
    @Test
    void testCreateUser_UserRepositoryFindByEmailReturnsAbsent() throws Exception {
    // Setup
                     final UserService userServiceUnderTest = new UserService(mockUserRepository) ;
        // TODO: Set the following fields: userCache.
                final UserRequestDTO userRequestDTO = new UserRequestDTO("username", "email", "phoneNumber");
        when( mockUserRepository .findByEmail("email")).thenReturn( Optional.empty() );
                
            // Configure UserRepository.save(...).
        final User user = new User();
                user.setId("id");
                user.setUsername("username");
                user.setEmail("email");
                user.setPhoneNumber("phoneNumber");
        final User entity = new User();
                entity.setId("id");
                entity.setUsername("username");
                entity.setEmail("email");
                entity.setPhoneNumber("phoneNumber");
            when( mockUserRepository .save(entity)).thenReturn(user);

    // Run the test
 final CompletableFuture<PublicResponseDTO<UserResponseDTO>> result =  userServiceUnderTest.createUser(userRequestDTO);

        // Verify the results
        // Confirm ConcurrentHashMap.put(...).
        final User value = new User();
                value.setId("id");
                value.setUsername("username");
                value.setEmail("email");
                value.setPhoneNumber("phoneNumber");
            verify( mockUserCache ).put("id",value);
    }
                                                                                
    @Test
    void testGetUserById() throws Exception {
    // Setup
                     final UserService userServiceUnderTest = new UserService(mockUserRepository) ;
        // TODO: Set the following fields: userCache.
            // Configure ConcurrentHashMap.get(...).
        final User user = new User();
                user.setId("id");
                user.setUsername("username");
                user.setEmail("email");
                user.setPhoneNumber("phoneNumber");
            when( mockUserCache .get("userId")).thenReturn(user);

    // Run the test
 final CompletableFuture<PublicResponseDTO<UserResponseDTO>> result =  userServiceUnderTest.getUserById("userId");

        // Verify the results
    }
                                                                                                
    @Test
    void testGetUserById_ConcurrentHashMapGetReturnsNull() throws Exception {
    // Setup
                     final UserService userServiceUnderTest = new UserService(mockUserRepository) ;
        // TODO: Set the following fields: userCache.
                when( mockUserCache .get("userId")).thenReturn( null );
                
            // Configure UserRepository.findById(...).
                                                        final User user1 = new User();
                user1.setId("id");
                user1.setUsername("username");
                user1.setEmail("email");
                user1.setPhoneNumber("phoneNumber");
        final Optional<User> user = Optional.of(user1);
            when( mockUserRepository .findById("userId")).thenReturn(user);

    // Run the test
 final CompletableFuture<PublicResponseDTO<UserResponseDTO>> result =  userServiceUnderTest.getUserById("userId");

        // Verify the results
        // Confirm ConcurrentHashMap.put(...).
        final User value = new User();
                value.setId("id");
                value.setUsername("username");
                value.setEmail("email");
                value.setPhoneNumber("phoneNumber");
            verify( mockUserCache ).put("id",value);
    }
                                                                        
    @Test
    void testGetUserById_UserRepositoryReturnsAbsent() throws Exception {
    // Setup
                     final UserService userServiceUnderTest = new UserService(mockUserRepository) ;
        // TODO: Set the following fields: userCache.
                when( mockUserCache .get("userId")).thenReturn( null );
        when( mockUserRepository .findById("userId")).thenReturn( Optional.empty() );

    // Run the test
 final CompletableFuture<PublicResponseDTO<UserResponseDTO>> result =  userServiceUnderTest.getUserById("userId");

        // Verify the results
    }
                                                
    @Test
    void testUpdateUserById() throws Exception {
    // Setup
                     final UserService userServiceUnderTest = new UserService(mockUserRepository) ;
        // TODO: Set the following fields: userCache.
                final UserRequestDTO userRequestDTO = new UserRequestDTO("username", "email", "phoneNumber");
                
            // Configure UserRepository.findById(...).
                                                        final User user1 = new User();
                user1.setId("id");
                user1.setUsername("username");
                user1.setEmail("email");
                user1.setPhoneNumber("phoneNumber");
        final Optional<User> user = Optional.of(user1);
            when( mockUserRepository .findById("userId")).thenReturn(user);

            // Configure UserRepository.save(...).
        final User user2 = new User();
                user2.setId("id");
                user2.setUsername("username");
                user2.setEmail("email");
                user2.setPhoneNumber("phoneNumber");
        final User entity = new User();
                entity.setId("id");
                entity.setUsername("username");
                entity.setEmail("email");
                entity.setPhoneNumber("phoneNumber");
            when( mockUserRepository .save(entity)).thenReturn(user2);

    // Run the test
 final CompletableFuture<PublicResponseDTO<UserResponseDTO>> result =  userServiceUnderTest.updateUserById("userId",userRequestDTO);

        // Verify the results
        // Confirm ConcurrentHashMap.put(...).
        final User value = new User();
                value.setId("id");
                value.setUsername("username");
                value.setEmail("email");
                value.setPhoneNumber("phoneNumber");
            verify( mockUserCache ).put("id",value);
    }
                                                                                                
    @Test
    void testUpdateUserById_UserRepositoryFindByIdReturnsAbsent() throws Exception {
    // Setup
                     final UserService userServiceUnderTest = new UserService(mockUserRepository) ;
        // TODO: Set the following fields: userCache.
                final UserRequestDTO userRequestDTO = new UserRequestDTO("username", "email", "phoneNumber");
        when( mockUserRepository .findById("userId")).thenReturn( Optional.empty() );

    // Run the test
 final CompletableFuture<PublicResponseDTO<UserResponseDTO>> result =  userServiceUnderTest.updateUserById("userId",userRequestDTO);

        // Verify the results
    }
                                                                                
    @Test
    void testDeleteUserById() throws Exception {
    // Setup
                     final UserService userServiceUnderTest = new UserService(mockUserRepository) ;
        // TODO: Set the following fields: userCache.
            // Configure UserRepository.findById(...).
                                                        final User user1 = new User();
                user1.setId("id");
                user1.setUsername("username");
                user1.setEmail("email");
                user1.setPhoneNumber("phoneNumber");
        final Optional<User> user = Optional.of(user1);
            when( mockUserRepository .findById("userId")).thenReturn(user);

    // Run the test
 final CompletableFuture<PublicResponseDTO<Object>> result =  userServiceUnderTest.deleteUserById("userId");

        // Verify the results
        // Confirm UserRepository.delete(...).
        final User entity = new User();
                entity.setId("id");
                entity.setUsername("username");
                entity.setEmail("email");
                entity.setPhoneNumber("phoneNumber");
            verify( mockUserRepository ).delete(entity);
            verify( mockUserCache ).remove("userId");
    }
                                                                                                
    @Test
    void testDeleteUserById_UserRepositoryFindByIdReturnsAbsent() throws Exception {
    // Setup
                     final UserService userServiceUnderTest = new UserService(mockUserRepository) ;
        // TODO: Set the following fields: userCache.
                when( mockUserRepository .findById("userId")).thenReturn( Optional.empty() );

    // Run the test
 final CompletableFuture<PublicResponseDTO<Object>> result =  userServiceUnderTest.deleteUserById("userId");

        // Verify the results
    }
                                                                }

