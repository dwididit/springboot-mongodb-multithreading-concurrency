package dev.dwidi.springbootmongodbmultithreadingconcurrency.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {

    @Test
    void getId() {
        User user = new User();
        user.setId("123");
        assertEquals("123", user.getId());
    }

    @Test
    void getUsername() {
        User user = new User();
        user.setUsername("testuser");
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void getEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void getPhoneNumber() {
        User user = new User();
        user.setPhoneNumber("1234567890");
        assertEquals("1234567890", user.getPhoneNumber());
    }

    @Test
    void setId() {
        User user = new User();
        user.setId("123");
        assertEquals("123", user.getId());
    }

    @Test
    void setUsername() {
        User user = new User();
        user.setUsername("testuser");
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void setEmail() {
        User user = new User();
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void setPhoneNumber() {
        User user = new User();
        user.setPhoneNumber("1234567890");
        assertEquals("1234567890", user.getPhoneNumber());
    }

    @Test
    void equalsAndHashCode() {
        User user1 = new User();
        user1.setId("123");
        user1.setUsername("testuser");
        user1.setEmail("test@example.com");
        user1.setPhoneNumber("1234567890");

        User user2 = new User();
        user2.setId("123");
        user2.setUsername("testuser");
        user2.setEmail("test@example.com");
        user2.setPhoneNumber("1234567890");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void toStringMethod() {
        User user = new User();
        user.setId("123");
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPhoneNumber("1234567890");

        String expected = "User(id=123, username=testuser, email=test@example.com, phoneNumber=1234567890)";
        assertEquals(expected, user.toString());
    }
}