package dev.dwidi.springbootmongodbmultithreadingconcurrency.repository;

import dev.dwidi.springbootmongodbmultithreadingconcurrency.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The UserRepository interface is a repository for the User entity.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    default String findUsernameByEmail(String email) {
        return findByEmail(email).map(User::getUsername).orElse(null);
    }
}
