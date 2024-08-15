package dev.dwidi.springbootmongodbmultithreadingconcurrency.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
