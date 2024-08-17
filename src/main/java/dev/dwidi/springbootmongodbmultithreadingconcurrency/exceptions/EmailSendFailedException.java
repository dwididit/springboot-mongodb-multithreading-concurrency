package dev.dwidi.springbootmongodbmultithreadingconcurrency.exceptions;

public class EmailSendFailedException extends RuntimeException {
    public EmailSendFailedException(String message, Exception e) {
        super(message, e);
    }
}
