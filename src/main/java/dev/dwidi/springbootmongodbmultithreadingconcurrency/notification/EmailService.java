package dev.dwidi.springbootmongodbmultithreadingconcurrency.notification;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    CompletableFuture<Void> sendEmail(String to, String from, String subject, String body);
}